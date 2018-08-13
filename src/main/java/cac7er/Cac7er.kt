package cac7er

import java.io.*

object Cac7er {
   val MAJOR_VERSION = 1
   val MINOR_VERSION = 0
   val REVISION      = 0

   val VERSION = "$MAJOR_VERSION.$MINOR_VERSION.$REVISION"

   /**
    * loads a [Cache].
    *
    * @throws IOException
    * @since 1.0.0
    */
   fun <T> load(file: File): WritableCache<T> {
      TODO()
   }

   /**
    * writes the content into the [file][Cache.file].
    *
    * @throws IOException
    * @since 1.0.0
    */
   fun save(file: File, content: Any) {
      TODO()
   }

   /**
    * runs the [gc] for all files in the specified directory.
    *
    * @since 1.0.0
    */
   fun gc(idealTotalFileSize: Long, dir: File) {
      TODO()
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
    * val aCache = Cache(File("~/.foo/cache/a"), A())
    * val bCache = Cache(File("~/.foo/cache/b"), B(aCache))
    *
    * // ...
    *
    * Cac7er.gc(100L, listOf(aCache.file, bCache.file))
    * ```
    *
    * Then, if `b` is not deleted, `a` is never deleted. If `a` is deleted, `b`
    * is also deleted.
    *
    * @param idealTotalFileSize The ideal total size of files.
    *
    * @throws IOException when any file could not be loaded
    *
    * @throws IllegalArgumentException
    *   When any cache of the specified files depends on other cache not in the
    *   specified files.
    *
    * @see Cache.get
    *
    * @see WeakCache
    *
    * @since 1.0.0
    */
   fun gc(idealTotalFileSize: Long, files: Iterable<File>) {
      TODO()
   }
}
