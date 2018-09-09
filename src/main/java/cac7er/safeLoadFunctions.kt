package cac7er

import java.io.*

suspend fun <K, V> Repository<K, V>.loadOrNull(key: K): Cache<V>? {
   return try {
      load(key)
   } catch (e: Exception) {
      null
   }
}

suspend fun <K, V> WritableRepository<K, V>.loadOrNull(key: K): WritableCache<V>? {
   return try {
      load(key)
   } catch (e: Exception) {
      null
   }
}

suspend fun <K, V> Repository<K, V>.loadWeakCacheOrNull(key: K): WeakCache<V>? {
   return try {
      loadWeakCache(key)
   } catch (e: Exception) {
      null
   }
}

suspend fun <K, V> WritableRepository<K, V>
      .loadWeakCacheOrNull(key: K): WritableWeakCache<V>?
{
   return try {
      loadWeakCache(key)
   } catch (e: IOException) {
      null
   }
}

suspend fun <T> LazyCache<T>.getOrNull(accessCount: Float = .0f): T? {
   return try {
      get(accessCount)
   } catch (e: IOException) {
      null
   }
}
