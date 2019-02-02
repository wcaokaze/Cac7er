package cac7er

import java.lang.ref.*

/**
 * The interface between a Kotlin instance and a file.
 *
 * NOTE: It looks like immutable but actually not. [save][WritableRepository.save]
 * or other functions may replace the instance.
 *
 * @param T The type of the cached instance.
 * @see Cac7er
 * @since 0.1.0
 */
interface Cache<out T> {
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
    *   when the cached instance is not an instance of [T]. In the strict sense,
    *   [ClassCastException] is thrown by a cast operation complemented by
    *   the compiler.
    *
    * @since 0.1.0
    */
   fun get(time: Long, accessCount: Float = 0.0f): T

   fun get(accessCount: Float = 0.0f): T
         = get(System.currentTimeMillis(), accessCount)

   /**
    * adds a function to observe this cache. Note that observers are referenced
    * as [WeakReference]. Simplex lambda will be collected by GC. To avoid GC,
    * the observer instance should be owned by any other instance. The easiest
    * way is using [addObserver(Any, (Cache<T>, T) -> Unit)][addObserver].
    *
    * @since 0.3.0
    */
   fun addObserver(observer: (Cache<T>, T) -> Unit)

   /**
    * As mentioned in another overload, observers are referenced as
    * [WeakReference]. In this function the observer is associated with the
    * specified owner instance, and can observe until the owner is GCed.
    *
    * @since 0.3.0
    */
   fun addObserver(owner: Any, observer: (Cache<T>, T) -> Unit)

   /**
    * removes the observer. The name says it all.
    * @since 0.3.0
    */
   fun removeObserver(observer: (Cache<T>, T) -> Unit)

   /**
    * converts this Cache to [LazyCache].
    * @since 0.1.0
    */
   fun toLazyCache(): LazyCache<T>

   /**
    * converts this Cache to [WeakCache].
    * @since 0.1.0
    */
   fun toWeakCache(): WeakCache<T>

   /**
    * converts this Cache to [LazyWeakCache].
    * @since 0.7.0
    */
   fun toLazyWeakCache(): LazyWeakCache<T>
}

/**
 * Writable Cache. The name says it all.
 * @since 0.1.0
 */
interface WritableCache<T> : Cache<T> {
   /**
    * caches the new instance.
    *
    * This function replaces the instance on JVM memory and returns immediately.
    * Some other thread writes it into the file at some future time.
    *
    * @since 0.1.0
    */
   fun save(content: T)

   /**
    * converts this WritableCache to [WritableLazyCache].
    * @since 0.1.0
    */
   override fun toLazyCache(): WritableLazyCache<T>

   /**
    * converts this WritableCache to [WritableWeakCache].
    * @since 0.1.0
    */
   override fun toWeakCache(): WritableWeakCache<T>

   /**
    * converts this WritableCache to [WritableLazyWeakCache].
    * @since 0.7.0
    */
   override fun toLazyWeakCache(): WritableLazyWeakCache<T>
}
