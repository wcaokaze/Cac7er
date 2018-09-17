package cac7er

import java.io.*
import cac7er.serializer.*

class Cac7er {
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
       */
      operator fun invoke(name: String, dir: File,
                          builderAction: Builder.() -> Unit): Cac7er
      {
         TODO()
      }
   }

   val name: String = TODO()
   val dir: File = TODO()

   class Builder {
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
         TODO()
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
       *    delegatee += userCac7er
       *
       *    statusRepository = createRepository("statuses",
       *          CacheOutput::writeStatus, CacheInput::readStatus)
       * }
       * ```
       */
      val delegatee: MutableSet<Cac7er> = TODO()
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
