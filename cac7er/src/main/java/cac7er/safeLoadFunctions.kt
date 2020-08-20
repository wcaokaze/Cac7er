package cac7er

import kotlinx.coroutines.*

/**
 * loads a [Cache] or returns null if failed
 * @return a [Cache] or null
 * @since 0.1.0
 */
suspend fun <K, V> Repository<K, V>.loadOrNull(key: K): Cache<V>? {
   return try {
      load(key)
   } catch (e: Exception) {
      null
   }
}

/**
 * loads a [Cache] or returns null if failed
 * @return a [Cache] or null
 * @since 0.1.0
 */
suspend fun <K, V> WritableRepository<K, V>.loadOrNull(key: K): WritableCache<V>? {
   return try {
      load(key)
   } catch (e: Exception) {
      null
   }
}

/**
 * loads a [Cache] or throws [CancellationException] if failed
 * @throws CancellationException when failed to load a [Cache]
 * @since 0.2.0
 */
suspend fun <K, V> Repository<K, V>.loadOrCancel(key: K): Cache<V> {
   return try {
      load(key)
   } catch (e: Exception) {
      throw CancellationException()
   }
}

/**
 * loads a [Cache] or throws [CancellationException] if failed
 * @throws CancellationException when failed to load a [Cache]
 * @since 0.2.0
 */
suspend fun <K, V> WritableRepository<K, V>.loadOrCancel(key: K): WritableCache<V> {
   return try {
      load(key)
   } catch (e: Exception) {
      throw CancellationException()
   }
}

// =============================================================================

/**
 * loads a [WeakCache] or returns null if failed
 * @return a [WeakCache] or null
 * @since 0.1.0
 */
suspend fun <K, V> Repository<K, V>.loadWeakCacheOrNull(key: K): WeakCache<V>? {
   return try {
      loadWeakCache(key)
   } catch (e: Exception) {
      null
   }
}

/**
 * loads a [WeakCache] or returns null if failed
 * @return a [WeakCache] or null
 * @since 0.1.0
 */
suspend fun <K, V> WritableRepository<K, V>
      .loadWeakCacheOrNull(key: K): WritableWeakCache<V>?
{
   return try {
      loadWeakCache(key)
   } catch (e: Exception) {
      null
   }
}

/**
 * loads a [WeakCache] or throws [CancellationException] if failed
 * @throws CancellationException when failed to load a [WeakCache]
 * @since 0.2.0
 */
suspend fun <K, V> Repository<K, V>.loadWeakCacheOrCancel(key: K): WeakCache<V> {
   return try {
      loadWeakCache(key)
   } catch (e: Exception) {
      throw CancellationException()
   }
}

/**
 * loads a [WeakCache] or throws [CancellationException] if failed
 * @throws CancellationException when failed to load a [WeakCache]
 * @since 0.2.0
 */
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

/**
 * loads the content or returns null if failed
 * @return the content of LazyCache or null
 * @since 0.1.0
 */
suspend fun <T> LazyCache<T>.getOrNull(accessCount: Float = 0.0f): T? {
   return try {
      get(accessCount)
   } catch (e: Exception) {
      null
   }
}

/**
 * loads the content or throws [CancellationException] if failed
 * @throws CancellationException when failed to load the content of LazyCache
 * @since 0.2.0
 */
suspend fun <T> LazyCache<T>.getOrCancel(accessCount: Float = 0.0f): T {
   return try {
      get(accessCount)
   } catch (e: Exception) {
      throw CancellationException()
   }
}
