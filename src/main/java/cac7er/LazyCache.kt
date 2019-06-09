package cac7er

import java.io.*

/**
 * Cache which doesn't load the content until calling [get].
 *
 * Whenever consumers get the content, they have to call a suspend function.
 *
 * @since 0.1.0
 */
interface LazyCache<out T> : Observable<T> {
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
    * @throws IOException
    *
    * @throws ClassCastException
    *   when the cached instance is not an instance of [T]. In the strict sense,
    *   [ClassCastException] is thrown by a cast operation complemented by
    *   the compiler.
    *
    * @since 0.1.0
    */
   suspend fun get(time: Long, accessCount: Float = 0.0f): T

   suspend fun get(accessCount: Float = 0.0f): T
         = get(System.currentTimeMillis(), accessCount)

   /**
    * @return whether the cached instance is already exists on JVM memory.
    * @since 0.3.0
    */
   val hasContent: Boolean

   /**
    * returns the cached instance if it already exists on JVM memory, otherwise
    * returns `null`.
    *
    * time and accessCount only affect when the cached instance is returned.
    *
    * @return the cached instance if it exists on JVM memory, otherwise `null`.
    * @throws ClassCastException
    * @since 0.1.0
    */
   fun getIfAlreadyLoaded(time: Long = System.currentTimeMillis(),
                          accessCount: Float = 0.0f): T?

   /**
    * adds a function to observe this cache.
    *
    * If you are good at RxJava, you may prefer
    * [RxCac7er](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/RxCac7er)
    *
    * The observer also will be called when [get] has been completed to load the
    * content.
    *
    * @since 0.3.0
    */
   override fun addObserver(observer: (Cache<T>, T) -> Unit)

   /**
    * converts this LazyCache to [Cache].
    * @since 0.1.0
    */
   suspend fun toCache(): Cache<T>

   /**
    * converts this LazyCache to [WeakCache].
    * @since 0.1.0
    */
   suspend fun toWeakCache(): WeakCache<T>
}

/**
 * Writable LazyCache. The name says it all.
 * @since 0.1.0
 */
interface WritableLazyCache<T> : LazyCache<T> {
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
    * converts this WritableLazyCache to [WritableCache].
    * @since 0.1.0
    */
   override suspend fun toCache(): WritableCache<T>

   /**
    * converts this WritableLazyCache to [WritableWeakCache].
    * @since 0.1.0
    */
   override suspend fun toWeakCache(): WritableWeakCache<T>
}
