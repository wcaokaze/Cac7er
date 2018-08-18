package cac7er.serializer

import cac7er.*
import cac7er.util.io.*

fun <T> CacheOutput.writeCache(value: Cache<T>) {
   val relativePath = RelativePathResolver
         .toRelativePath(baseCacheFile.path, value.file.path)

   dependence += relativePath

   writeString(relativePath)
}

fun <T> CacheInput.readCache(): Cache<T> {
   val relativePath = readString()
   RelativePathResolver.resolve(baseCacheFile.path, relativePath)
   TODO()
}
