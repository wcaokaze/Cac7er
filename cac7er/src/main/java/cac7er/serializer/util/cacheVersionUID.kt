package cac7er.serializer.util

import cac7er.serializer.*
import java.io.*

fun CacheOutput.writeCacheVersionUID(value: Int) {
   writeInt(value)
}

fun CacheInput.checkCacheVersionUID(expected: Int) {
   val uid = readInt()

   if (uid != expected) {
      throw IOException("cache version UID doesn't match. " +
            "expected $expected but $uid")
   }
}
