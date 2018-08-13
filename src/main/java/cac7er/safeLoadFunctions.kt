package cac7er

import java.io.*

fun <T> Cac7er.loadOrNull(file: File): WritableCache<T>? {
   return try {
      load(file)
   } catch (e: IOException) {
      null
   }
}

suspend fun <T> WeakCache<T>.getOrNull(
      time: Long = System.currentTimeMillis(),
      accessCount: Float = .0f
): T? {
   return try {
      get(time, accessCount)
   } catch (e: IOException) {
      null
   }
}

suspend fun <T> LazyCache<T>.getOrNull(
      time: Long = System.currentTimeMillis(),
      accessCount: Float = .0f
): T? {
   return try {
      get(time, accessCount)
   } catch (e: IOException) {
      null
   }
}
