package cac7er

import java.io.*
import java.lang.ref.*

/**
 * The interface between a Kotlin instance and a file.
 *
 * The Caches associated with the same file have the same instance.
 *
 * ```kotlin
 * println(cacheA       === cacheB)       // false
 * println(cacheA.file  ==  cacheB.file)  // true
 * println(cacheA.get() === cacheB.get()) // true
 * ```
 *
 * NOTE: It looks like immutable but actually not. [New Cache creation][invoke]
 * or other functions may replace the instance.
 *
 * @param T The type of the cached instance.
 * @see Cac7er
 * @since 1.0.0
 */
interface Cache<out T> {
   companion object {
      /**
       * creates a new instance of Cache.
       *
       * This may affect other instances that are associated with the same file,
       * in the same way as [WritableCache.save]. So *this function has a side
       * effect.*
       *
       * @since 1.0.0
       */
      operator fun <T> invoke(file: File, content: T): Cache<T> = TODO()
   }

   /**
    * The file which the instance is written in.
    * @since 1.0.0
    */
   val file: File

   /**
    * @param time
    *   the time when the Cache is accessed. [Cac7er.gc] makes this a criteria
    *   to consider how the Cache is important.
    *
    * @param accessCount
    *   the number of access. The type is [Float] for flexibility, infinite and
    *   negative values also work well if you can utilize it. But `NaN` is
    *   invalid (will be ignored).
    *
    * @return the cached instance
    *
    * @throws ClassCastException
    *   when the [file] doesn't save an instance of [T]. In the strict sense,
    *   [ClassCastException] is thrown by a cast operation complemented by
    *   the compiler.
    *
    * @since 1.0.0
    */
   fun get(time: Long = System.currentTimeMillis(), accessCount: Float = .0f): T

   /**
    * adds a function to observe this cache. Note that observers are referenced
    * as [WeakReference]. Simplex lambda will be collected by GC. To avoid GC,
    * observer functions should be owned by any other instance. The easiest way
    * is using [addObserver(Any, (T) -> Unit)][addObserver].
    *
    * @since 1.0.0
    */
   fun addObserver(observer: (T) -> Unit)

   /**
    * As mentioned in another overload, observers are referenced as
    * [WeakReference]. In this function the observer is associated with the
    * specified owner instance, and can observe until the owner is GCed.
    *
    * @since 1.0.0
    */
   fun addObserver(owner: Any, observer: (T) -> Unit)


   /**
    * removes the observer. The name says it all.
    * @since 1.0.0
    */
   fun removeObserver(observer: (T) -> Unit)

   /**
    * converts this Cache to [LazyCache].
    * @since 1.0.0
    */
   fun toLazyCache(): LazyCache<T>

   /**
    * converts this Cache to [WeakCache].
    * @since 1.0.0
    */
   fun toWeakCache(): WeakCache<T>
}

/**
 * Writable Cache. The name says it all.
 * @since 1.0.0
 */
interface WritableCache<T> : Cache<T> {
   companion object {
      /**
       * creates a new instance of WritableCache.
       *
       * This may affect other instances that are associated with the same file,
       * in the same way as [WritableCache.save]. So *this function has a side
       * effect.*
       *
       * @since 1.0.0
       */
      operator fun <T> invoke(file: File, content: T): WritableCache<T> = TODO()
   }

   /**
    * caches the new instance.
    *
    * This function replaces the instance on JVM memory and returns immediately.
    * Some other thread writes it into the file at some future time.
    *
    * @since 1.0.0
    */
   fun save(content: T)

   /**
    * converts this WritableCache to [WritableLazyCache].
    * @since 1.0.0
    */
   override fun toLazyCache(): WritableLazyCache<T>

   /**
    * converts this WritableCache to [WritableWeakCache].
    * @since 1.0.0
    */
   override fun toWeakCache(): WritableWeakCache<T>
}
