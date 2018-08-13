package cac7er

import java.io.File

/**
 * A facade for [Cac7er] to map keys to files like [kotlin.collections.Map].
 *
 * And this is more efficient than [Cac7er].
 *
 * @since 1.0.0
 */
interface CacheMap<in K, out V> {
   companion object {
      /**
       * @param dir
       *   The directory in which the cache files are written.
       *
       * @param fileNameSupplier
       *   The function which converts a key to a file in which the value is
       *   written. Should be a pure function.
       */
      operator fun <K, V> invoke(
            dir: File,
            fileNameSupplier: (key: K) -> String
      ): CacheMap<K, V> {
         TODO()
      }
   }

   /**
    * loads a Cache.
    *
    * This is similar to
    * ```kotlin
    * val file = File(dir, fileNameSupplier(key))
    * Cac7er.load(file)
    * ```
    *
    * @since 1.0.0
    */
   suspend fun get(key: K): Cache<V>
}

/**
 * Writable CacheMap. The name says it all.
 * @since 1.0.0
 */
interface WritableCacheMap<in K, V> : CacheMap<K, V> {
   companion object {
      /**
       * @param dir
       *   The directory in which the cache files are written.
       *
       * @param fileNameSupplier
       *   The function which converts a key to a file in which the value is
       *   written. Should be a pure function.
       */
      operator fun <K, V> invoke(
            dir: File,
            fileNameSupplier: (key: K) -> String
      ): WritableCacheMap<K, V> {
         TODO()
      }
   }

   override suspend fun get(key: K): WritableCache<V>

   /**
    * saves a cache.
    *
    * This is similar to
    * ```kotlin
    * val file = File(dir, fileNameSupplier(key))
    * Cac7er.save(file, value)
    * ```
    *
    * @since 1.0.0
    */
   fun save(key: K, value: V): WritableCache<V>
}
