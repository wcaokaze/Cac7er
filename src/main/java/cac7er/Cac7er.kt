package cac7er

import java.io.*
import cac7er.serializer.*

import kotlinx.coroutines.experimental.*

import java.util.LinkedList
import kotlin.collections.*

class Cac7er
      private constructor(
            val name: String,
            val dir: File,
            internal val repositories: Array<out Repository<*, *>?>
      )
      : CoroutineScope
{
   companion object {
      const val MAJOR_VERSION = 1
      const val MINOR_VERSION = 0
      const val REVISION      = 0

      const val VERSION = "$MAJOR_VERSION.$MINOR_VERSION.$REVISION"

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
       */
      operator fun invoke(name: String, dir: File,
                          builderAction: Builder.() -> Unit): Cac7er
      {
         return Builder().apply(builderAction).build(name, dir)
      }
   }

   private val job = Job()
   override val coroutineContext get() = job + Dispatchers.IO

   class Builder {
      private val repositories = LinkedList<RepositoryImpl<*, *>>()

      /**
       * equivalent to
       * ```
       * createRepository(name, { it.toString() },
       *       serializer, deserializer)
       * ```
       */
      fun <K, V> createRepository(name: String,
                                  serializer: Serializer<V>,
                                  deserializer: Deserializer<V>): Repository<K, V>
      {
         return createRepository(name, Any?::toString, serializer, deserializer)
      }

      fun <K, V> createRepository(name: String,
                                  fileNameSupplier: (K) -> String,
                                  serializer: Serializer<V>,
                                  deserializer: Deserializer<V>): Repository<K, V>
      {
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
       */
      val delegatees: MutableSet<Cac7er> = HashSet()

      internal fun build(name: String, dir: File): Cac7er {
         if (!dir.exists()) {
            if (!dir.mkdir()) throw IOException("can not mkdir: $dir")
         }

         val metadataFile = File(dir, name)

         val repoNames = Cac7erMetadataFileService.loadRepositoryNames(metadataFile)
         val repos = ArrayList<Repository<*, *>?>()

         repeat (repoNames.size) {
            repos += null as Repository<*, *>?
         }

         var added = false

         for (repo in repositories) {
            val repoName = name + '/' + repo.name
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

         val cac7er = Cac7er(name, dir, repos.toTypedArray())

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
    * Cac7er.gc(100L)
    * ```
    *
    * Then, if `b` is not deleted, `a` is never deleted. If `a` is deleted, `b`
    * is also deleted.
    *
    * @param idealTotalFileSize The ideal total size of files.
    * @throws IOException when any file could not be loaded
    * @see Cache.get
    * @see WeakCache
    * @since 1.0.0
    */
   fun gc(idealTotalFileSize: Long) {
      TODO()
   }
}
