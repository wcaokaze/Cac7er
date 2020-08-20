package cac7er

import java.io.*

/**
 * Cache which doesn't depend on the content. See [Cac7er.gc] first.
 *
 * ```kotlin
 * class A
 * class B(val aCache: WeakCache<A>)
 * //                  ^^^^
 *
 * val aCache = aRepository.save("a", A())
 * val bCache = bRepository.save("b", B(aCache))
 *
 * // ...
 *
 * Cac7er.gc(100L)
 * ```
 *
 * Then, [gc][Cac7er.gc] doesn't mind relationship, does delete `a` freely.
 *
 * @since 0.1.0
 */
interface WeakCache<out T> : Observable<T> {
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
    * @return the cached instance, or null if the cache was deleted by GC.
    *
    * @throws IOException
    *
    * @throws ClassCastException
    *   when the cached instance is not an instance of [T]. In the strict sense,
    *   [ClassCastException] is thrown by a cast operation complemented by
    *   the compiler.
    *
    * @since 0.1.0
    */
   fun get(time: Long, accessCount: Float = 0.0f): T?

   fun get(accessCount: Float = 0.0f): T?
         = get(System.currentTimeMillis(), accessCount)

   /**
    * converts this WeakCache to [Cache].
    *
    * @return Cache or null if the cache was deleted by GC.
    * @since 0.1.0
    */
   fun toCache(): Cache<T>?

   /**
    * converts this WeakCache to [LazyCache].
    *
    * @return LazyCache or null if the cache was deleted by GC.
    * @since 0.1.0
    */
   fun toLazyCache(): LazyCache<T>?
}

/**
 * Writable WeakCache. The name says it all.
 * @since 0.1.0
 */
interface WritableWeakCache<T> : WeakCache<T> {
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
    * converts this WritableWeakCache to [WritableCache].
    *
    * @return WritableCache or null if the cache was deleted by GC.
    * @since 0.1.0
    */
   override fun toCache(): WritableCache<T>?

   /**
    * converts this WritableWeakCache to [WritableLazyCache].
    *
    * @return WritableLazyCache or null if the cache was deleted by GC.
    * @since 0.1.0
    */
   override fun toLazyCache(): WritableLazyCache<T>?
}
