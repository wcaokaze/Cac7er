package cac7er

import java.io.*
import cac7er.serializer.*

import kotlinx.coroutines.*

import java.util.LinkedList
import kotlin.collections.*

import kotlin.contracts.*

/**
 * builds a new instance.
 *
 * Initialize repositories in the lambda.
 * ```kotlin
 * lateinit var userRepository: Repository<Long, User>
 *    private set
 *
 * lateinit var statusRepository: Repository<Long, Status>
 *    private set
 *
 * val cac7er = Cac7er("twitterCaches", File("path/to/directory")) {
 *    userRepository = createRepository("users",
 *          CacheOutput::writeUser, CacheInput::readUser)
 *
 *    statusRepository = createRepository("statuses",
 *          CacheOutput::writeStatus, CacheInput::readStatus)
 * }
 * ```
 *
 * see [README](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/README.md)
 * for more information.
 *
 * This can take time. If your Cac7er has many repositories, consider
 * instantiation with [launch][CoroutineScope.launch].
 *
 * @since 0.1.0
 */
@ExperimentalContracts
inline fun buildCac7er(name: String, dir: File,
                       builderAction: Cac7er.Builder.() -> Unit): Cac7er
{
   contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }

   return Cac7er.Builder(name).apply(builderAction).build(dir)
}

class Cac7er
      private constructor(
            val name: String,
            val dir: File,
            internal val idealTotalFileSize: Long,
            internal val repositories: Array<out RepositoryImpl<*, *>?>
      )
      : CoroutineScope
{
   companion object {
      val MAJOR_VERSION = 0
      val MINOR_VERSION = 6
      val REVISION      = 1

      val VERSION = "$MAJOR_VERSION.$MINOR_VERSION.$REVISION"
   }

   private val job = Job()
   override val coroutineContext get() = job + readerCoroutineDispatcher

   private var gcJob: Job? = null

   class Builder(private val cac7erName: String) {
      private val repositories = LinkedList<RepositoryImpl<*, *>>()

      /**
       * equivalent to
       * ```
       * createRepository(name, { it.toString() },
       *       serializer, deserializer)
       * ```
       *
       * @since 0.1.0
       */
      fun <K, V> createRepository(name: String,
                                  serializer: Serializer<V>,
                                  deserializer: Deserializer<V>)
            : WritableRepository<K, V>
      {
         return createRepository(name, Any?::toString, serializer, deserializer)
      }

      fun <K, V> createRepository(name: String,
                                  fileNameSupplier: (K) -> String,
                                  serializer: Serializer<V>,
                                  deserializer: Deserializer<V>)
            : WritableRepository<K, V>
      {
         if (name == cac7erName) {
            throw IllegalArgumentException(
                  "Repository cannot be named the same as its Cac7er")
         }

         val repo = RepositoryImpl(name, fileNameSupplier, serializer, deserializer)
         repositories += repo
         return repo
      }

      /**
       * Normally, a instance of Cac7er cannot manage repositories in another
       * Cac7er.
       *
       * ```kotlin
       * lateinit var statusRepository: Repository<Long, Status>
       * lateinit var userRepository:   Repository<Long, User>
       *
       * val statusCac7er = Cac7er("status", File("statuses")) {
       *    statusRepository = createRepository("statuses",
       *          CacheOutput::writeStatus, CacheInput::readStatus)
       * }
       *
       * val userCac7er = Cac7er("user", File("users")) {
       *    userRepository = createRepository("users",
       *          CacheOutput::writeUser, CacheInput::readUser)
       * }
       *
       * data class Status(
       *       val id: Long,
       *       val content: String,
       *       val userCache: Cache<User>
       * )
       *
       * data class User(
       *       val id: Long,
       *       val name: String
       * )
       * ```
       *
       * Here, `statusCac7er` can load a Cache of `Status`, but `statusCac7er`
       * *cannot* load a Cache of `User`. However `Status` has a Cache of `User`,
       * this causes a Exception.
       *
       * Yes, this is a solution.
       * ```kotlin
       * val statusCac7er = Cac7er("status", File("statuses")) {
       *    delegatees += userCac7er
       *
       *    statusRepository = createRepository("statuses",
       *          CacheOutput::writeStatus, CacheInput::readStatus)
       * }
       * ```
       *
       * @since 0.1.0
       */
      val delegatees: MutableSet<Cac7er> = HashSet()

      /**
       * The total size of cache files to run [gc].
       *
       * [WritableCache.save] and [WritableRepository.save] check the total
       * size and call [gc] if necessary.
       *
       * @since 0.1.0
       */
      var idealTotalFileSize: Long = Long.MAX_VALUE

      fun build(dir: File): Cac7er {
         val absDir = dir.absoluteFile

         if (!absDir.exists()) {
            if (!absDir.mkdirs()) throw IOException("can not mkdir: $absDir")
         }

         val metadataFile = File(absDir, cac7erName)

         val repoNames = Cac7erMetadataFileService.loadRepositoryNames(metadataFile)
         val repos = ArrayList<RepositoryImpl<*, *>?>()

         repeat (repoNames.size) {
            repos += null as RepositoryImpl<*, *>?
         }

         var added = false

         for (repo in repositories) {
            val repoName = cac7erName + '/' + repo.name
            val index = repoNames.indexOf(repoName)

            if (index >= 0) {
               repos[index] = repo
            } else {
               repos += repo
               repoNames += repoName
               added = true
            }
         }

         for (delegatee in delegatees) {
            for (delegateeRepo in delegatee.repositories) {
               if (delegateeRepo == null) continue

               val repoName = delegatee.name + '/' + delegateeRepo.name
               val index = repoNames.indexOf(repoName)

               if (index >= 0) {
                  repos[index] = delegateeRepo
               } else {
                  repos += delegateeRepo
                  repoNames += repoName
                  added = true
               }
            }
         }

         if (added) {
            Cac7erMetadataFileService
                  .writeRepositoryNames(metadataFile, repoNames)
         }

         val cac7er = Cac7er(cac7erName, absDir, idealTotalFileSize, repos.toTypedArray())

         for (repo in repositories) {
            repo.cac7er = cac7er
         }

         return cac7er
      }
   }

   /**
    * runs the garbage collector. GC deletes some files whose `importance` are
    * low.
    *
    * It is guaranteed that cache files that are depended by any other cache
    * which is not deleted are also not deleted. For example:
    *
    * ```kotlin
    * class A
    * class B(val aCache: Cache<A>)
    *
    * // ...
    *
    * val aCache = aRepository.save("a", A())
    * val bCache = bRepository.save("b", B(aCache))
    *
    * // ...
    *
    * cac7er.gc(100L)
    * ```
    *
    * Then, if `b` is not deleted, `a` is never deleted. If `a` is deleted, `b`
    * is also deleted.
    *
    * Note that Cac7er can delete files consider about its delegaters. In other
    * words, when your Cac7er delegates another Cac7er, the delegatee can delete
    * files even if your Cac7er depends on them.
    *
    * @param idealTotalFileSize The ideal total size of files.
    * @throws IOException when any file could not be loaded
    * @see Cache.get
    * @see WeakCache
    * @since 0.1.0
    */
   @Synchronized
   fun gc(idealTotalFileSize: Long): Job {
      if (gcJob?.isCompleted == false) return gcJob!!

      gcJob = launch(writerCoroutineDispatcher + SupervisorJob()) {
         val metadataList = loadAllMetadata()

         class Relationship {
            var dependees: List<Relationship> = emptyList()
            var importance = 0.0f
         }

         val relationshipMap = object : HashMap<File, Relationship>() {
            override operator fun get(key: File): Relationship {
               var v = super.get(key)

               if (v == null) {
                  v = Relationship()
                  this[key] = v
               }

               return v
            }
         }

         // ---- resolve dependency relationship
         for ((file, metadata) in metadataList) {
            relationshipMap[file].dependees =
                  metadata.dependence.map { relationshipMap[File(it)] }
         }

         // ---- set raw importance
         val currentPeriod = CirculationRecord.periodOf(System.currentTimeMillis())

         for ((file, metadata) in metadataList) {
            relationshipMap[file].importance =
                  metadata.circulationRecord.calcImportance(currentPeriod)
         }

         // ---- update the importance of depended files
         fun Relationship.updateImportanceIfNecessary(importance: Float) {
            if (importance <= this.importance) return

            this.importance = importance

            for (dependee in dependees) {
               dependee.updateImportanceIfNecessary(importance)
            }
         }

         for ((_, relationship) in relationshipMap) {
            for (dependee in relationship.dependees) {
               dependee.updateImportanceIfNecessary(relationship.importance)
            }
         }

         // ---- remove unaffectable records
         for ((file, metadata) in metadataList) {
            metadata.circulationRecord.removeUnaffectableSections(currentPeriod)

            RandomAccessFile(file, "rw").use {
               try {
                  it.seek(6L)

                  val circulationRecordPosition = it.readUnsignedShort().toLong()
                  it.seek(circulationRecordPosition)

                  metadata.circulationRecord.writeTo(it)
               } catch (e: IOException) {
                  // continue
               }
            }
         }

         // ---- delete files while totalFileSize > idealTotalFileSize

         /*
          * Files whose importance are equal must be deleted at once. So it needs
          * a list of lists of files.
          *
          *     [
          *        [ file1, file2 ], // Files whose importance is the lowest.
          *        [ file3 ],        // The 2nd lowest
          *        // ...            // And so on.
          *     ]
          */
         val sortedFileList = run {
            val fileListMappedImportance = relationshipMap.asIterable()
                  .groupBy({ it.value.importance }, { it.key })

            fileListMappedImportance.asSequence()
                  .sortedBy { it.key }
                  .map { it.value }
         }

         var totalFileSize = sortedFileList
               .flatten()
               .map { it.length() }
               .sum()

         for (fileList in sortedFileList) {
            if (totalFileSize <= idealTotalFileSize) break

            for (file in fileList) {
               totalFileSize -= file.length()
               file.delete()
            }
         }
      }

      return gcJob!!
   }

   /**
    * for [Builder.idealTotalFileSize]
    *
    * @since 0.1.0
    */
   internal fun autoGc() {
      if (idealTotalFileSize != Long.MAX_VALUE) {
         gc(idealTotalFileSize)
      }
   }

   private fun loadAllMetadata(): List<Pair<File, Metadata>> {
      val allMetadata = ArrayList<Pair<File, Metadata>>()

      for (repo in repositories) {
         if (repo == null) continue

         for (file in repo.dir.listFiles()) {
            if (file.isDirectory) continue

            val metadata = try {
               loadMetadata(file)
            } catch (e: Throwable) {
               continue
            }

            allMetadata += file to metadata
         }
      }

      return allMetadata
   }
}
