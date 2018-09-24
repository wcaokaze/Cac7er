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
   val relativePath = readString()
   RelativePathResolver.resolve(baseCacheFile.path, relativePath)
   TODO()
}
