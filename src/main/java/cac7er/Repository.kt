package cac7er

import java.io.IOException

/**
 * Repository of [Cache]s.
 *
 * @since 0.1.0
 */
interface Repository<in K, out V> {
   val name: String

   /**
    * loads a [Cache].
    *
    * @throws IOException
    * @since 0.1.0
    */
   suspend fun load(key: K): Cache<V>

   /**
    * loads a [WeakCache].
    *
    * NOTE:
    * Even if the file doesn't exist, a [WeakCache] can be returned as
    * a representation that the cache was deleted by [GC][Cac7er.gc].
    *
    * @throws IOException
    * @since 0.1.0
    */
   suspend fun loadWeakCache(key: K): WeakCache<V>

   /**
    * returns a [LazyCache]. This is not a suspend function since loading is
    * lazy (see [LazyCache.get]).
    *
    * @since 0.1.0
    */
   fun loadLazyCache(key: K): LazyCache<V>

   /**
    * adds a function to observe a key.
    * @since 0.3.0
    */
   fun addObserver(key: K, observer: (Cache<V>, V) -> Unit)

   /**
    * removes the observer. The name says it all.
    * @since 0.3.0
    */
   fun removeObserver(key: K, observer: (Cache<V>, V) -> Unit)
}

/**
 * Writable Repository. The name says it all.
 * @since 0.1.0
 */
interface WritableRepository<in K, V> : Repository<K, V> {
   override suspend fun load(key: K): WritableCache<V>
   override suspend fun loadWeakCache(key: K): WritableWeakCache<V>
   override fun loadLazyCache(key: K): WritableLazyCache<V>

   /**
    * saves a cache.
    *
    * @since 0.1.0
    */
   fun save(key: K, value: V): WritableCache<V>
}
