package cac7er.serializer

import cac7er.*

import java.io.*
import cac7er.util.io.*

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
      throw IOException(
            "Cac7er ${cac7er.name} cannot save a Cache of Cac7er ${cache.cac7er.name}. " +
            "Consider adding ${cache.cac7er.name} into ${cac7er.name} as a delegatee.")
   }

   writeInt(index)
   writeString(cache.fileName)

   val targetFile = File(repository.dir, cache.fileName)

   val relativePath = RelativePathResolver
         .toRelativePath(file.path, targetFile.path)

   dependence += relativePath
}

fun <T> CacheInput.readCache(): Cache<T> {
   val repositoryIndex = readInt()
   val fileName = readString()

   val repository = cac7er.repositories[repositoryIndex]
         ?: throw IOException("Cac7er which wrote the file is not in delegatee.")

   @Suppress("UNCHECKED_CAST")
   return repository.loadBlocking(fileName) as Cache<T>
}

fun <T> CacheOutput.writeLazyCache(value: LazyCache<T>) {
   val cache = value as? LazyCacheImpl<T> ?: throw IllegalArgumentException()
   val repository = cache.repository

   val index = cac7er.repositories.indexOf(repository)

   if (index == -1) {
      throw IOException(
            "Cac7er ${cac7er.name} cannot save a Cache of Cac7er ${cache.cac7er.name}. " +
            "Consider adding ${cache.cac7er.name} into ${cac7er.name} as a delegatee.")
   }

   writeInt(index)
   writeString(cache.fileName)

   val targetFile = File(repository.dir, cache.fileName)

   val relativePath = RelativePathResolver
         .toRelativePath(file.path, targetFile.path)

   dependence += relativePath
}

fun <T> CacheInput.readLazyCache(): LazyCache<T> {
   val repositoryIndex = readInt()
   val fileName = readString()

   val repository = cac7er.repositories[repositoryIndex]
         ?: throw IOException("Cac7er which wrote the file is not in delegatee.")

   @Suppress("UNCHECKED_CAST")
   return repository.loadLazyCacheBlocking(fileName) as LazyCache<T>
}

fun <T> CacheOutput.writeWeakCache(value: WeakCache<T>) {
   val cache = value as? WeakCacheImpl<T> ?: throw IllegalArgumentException()
   val repository = cache.repository

   val index = cac7er.repositories.indexOf(repository)

   if (index == -1) {
      throw IOException(
            "Cac7er ${cac7er.name} cannot save a Cache of Cac7er ${cache.cac7er.name}. " +
            "Consider adding ${cache.cac7er.name} into ${cac7er.name} as a delegatee.")
   }

   writeInt(index)
   writeString(cache.fileName)
}

fun <T> CacheInput.readWeakCache(): WeakCache<T> {
   val repositoryIndex = readInt()
   val fileName = readString()

   val repository = cac7er.repositories[repositoryIndex]
         ?: throw IOException("Cac7er which wrote the file is not in delegatee.")

   @Suppress("UNCHECKED_CAST")
   return repository.loadWeakCacheBlocking(fileName) as WeakCache<T>
}
