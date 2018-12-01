package cac7er

import kotlinx.coroutines.*

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

suspend fun <K, V> Repository<K, V>.loadOrCancel(key: K): Cache<V> {
   return try {
      load(key)
   } catch (e: Exception) {
      throw CancellationException()
   }
}

suspend fun <K, V> WritableRepository<K, V>.loadOrCancel(key: K): WritableCache<V> {
   return try {
      load(key)
   } catch (e: Exception) {
      throw CancellationException()
   }
}

// =============================================================================

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
   } catch (e: Exception) {
      null
   }
}

suspend fun <K, V> Repository<K, V>.loadWeakCacheOrCancel(key: K): WeakCache<V> {
   return try {
      loadWeakCache(key)
   } catch (e: Exception) {
      throw CancellationException()
   }
}

suspend fun <K, V> WritableRepository<K, V>
      .loadWeakCacheOrCancel(key: K): WritableWeakCache<V>
{
   return try {
      loadWeakCache(key)
   } catch (e: Exception) {
      throw CancellationException()
   }
}

// =============================================================================

suspend fun <T> LazyCache<T>.getOrNull(accessCount: Float = .0f): T? {
   return try {
      get(accessCount)
   } catch (e: Exception) {
      null
   }
}

suspend fun <T> LazyCache<T>.getOrCancel(accessCount: Float = .0f): T {
   return try {
      get(accessCount)
   } catch (e: Exception) {
      throw CancellationException()
   }
}
