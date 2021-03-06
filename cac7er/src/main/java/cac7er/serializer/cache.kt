package cac7er.serializer

import cac7er.*

import java.io.*

/*
 *            delegatee
 *   Cac7er      ←      Cac7er
 *     ↕                  ↕
 * Repository          CacheOutput
 *     ↕
 * Uniformizer
 *     ↑
 *    value
 */
fun <T> CacheOutput.writeCache(value: Cache<T>) {
   val cache = value as? CacheImpl<T> ?: throw IllegalArgumentException()
   val repository = cache.repository

   val index = cac7er.repositories.indexOf(repository)

   if (index == -1) {
      val delegater = '"' + cac7er.name       + '"'
      val delegatee = '"' + cache.cac7er.name + '"'

      throw IOException(
            "Cac7er $delegater cannot save a Cache of Cac7er $delegatee. " +
            "Consider adding $delegatee into $delegater as a delegatee.")
   }

   writeInt(index)
   writeString(cache.fileName)

   val targetFile = File(repository.dir, cache.fileName)

   dependence += targetFile.absolutePath
}

/** @since 0.6.1 */
fun <T> CacheOutput.writeWritableCache(value: WritableCache<T>)
      = writeCache(value)

fun <T> CacheInput.readCache(): Cache<T>
      = readWritableCache()

/** @since 0.6.1 */
fun <T> CacheInput.readWritableCache(): WritableCache<T> {
   val repositoryIndex = readInt()
   val fileName = readString()

   val repository = cac7er.repositories[repositoryIndex]
         ?: throw IOException("Cac7er which wrote the file is not in delegatee.")

   @Suppress("UNCHECKED_CAST")
   return repository.loadBlocking(fileName) as WritableCache<T>
}

// =============================================================================

fun <T> CacheOutput.writeLazyCache(value: LazyCache<T>) {
   val cache = value as? LazyCacheImpl<T> ?: throw IllegalArgumentException()
   val repository = cache.repository

   val index = cac7er.repositories.indexOf(repository)

   if (index == -1) {
      val delegater = '"' + cac7er.name       + '"'
      val delegatee = '"' + cache.cac7er.name + '"'

      throw IOException(
            "Cac7er $delegater cannot save a Cache of Cac7er $delegatee. " +
            "Consider adding $delegatee into $delegater as a delegatee.")
   }

   writeInt(index)
   writeString(cache.fileName)

   val targetFile = File(repository.dir, cache.fileName)

   dependence += targetFile.absolutePath
}

/** @since 0.6.1 */
fun <T> CacheOutput.writeWritableLazyCache(value: WritableLazyCache<T>)
      = writeLazyCache(value)

fun <T> CacheInput.readLazyCache(): LazyCache<T>
      = readWritableLazyCache()

/** @since 0.6.1 */
fun <T> CacheInput.readWritableLazyCache(): WritableLazyCache<T> {
   val repositoryIndex = readInt()
   val fileName = readString()

   val repository = cac7er.repositories[repositoryIndex]
         ?: throw IOException("Cac7er which wrote the file is not in delegatee.")

   @Suppress("UNCHECKED_CAST")
   return repository.loadLazyCacheBlocking(fileName) as WritableLazyCache<T>
}

// =============================================================================

fun <T> CacheOutput.writeWeakCache(value: WeakCache<T>) {
   val cache = value as? WeakCacheImpl<T> ?: throw IllegalArgumentException()
   val repository = cache.repository

   val index = cac7er.repositories.indexOf(repository)

   if (index == -1) {
      val delegater = '"' + cac7er.name       + '"'
      val delegatee = '"' + cache.cac7er.name + '"'

      throw IOException(
            "Cac7er $delegater cannot save a Cache of Cac7er $delegatee. " +
            "Consider adding $delegatee into $delegater as a delegatee.")
   }

   writeInt(index)
   writeString(cache.fileName)
}

/** @since 0.6.1 */
fun <T> CacheOutput.writeWritableWeakCache(value: WritableWeakCache<T>)
      = writeWeakCache(value)

/** @since 0.6.1 */
fun <T> CacheInput.readWeakCache(): WeakCache<T>
      = readWritableWeakCache()

fun <T> CacheInput.readWritableWeakCache(): WritableWeakCache<T> {
   val repositoryIndex = readInt()
   val fileName = readString()

   val repository = cac7er.repositories[repositoryIndex]
         ?: throw IOException("Cac7er which wrote the file is not in delegatee.")

   @Suppress("UNCHECKED_CAST")
   return repository.loadWeakCacheBlocking(fileName) as WritableWeakCache<T>
}
