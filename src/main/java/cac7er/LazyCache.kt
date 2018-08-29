package cac7er

import java.io.*
import java.lang.ref.*

/**
 * Cache which doesn't load the content until calling [get].
 *
 * These basic behaviors seem like [Cache]. The Caches associated with the same
 * file have the same instance. [gc][Cac7er.gc] doesn't delete caches that are
 * depended by any other LazyCache which is not deleted. (see [gc][Cac7er.gc])
 * But in Kotlin codes, this is similar to [WeakCache] rather than [Cache].
 * The content is stored as [SoftReference], whenever consumers get the content,
 * they have to call a suspend function. In short, this is like [Cache] on
 * behaviors regarding [gc][Cac7er.gc], but is like [WeakCache] on behaviors
 * regarding de-serialization.
 *
 * @since 1.0.0
 */
interface LazyCache<out T> {
   companion object {
      /**
       * creates a new instance of LazyCache.
       *
       * This may affect other instances that are associated with the same file,
       * in the same way as [WritableLazyCache.save]. So *this function has a
       * side effect.*
       *
       * @since 1.0.0
       */
      operator fun <T> invoke(file: File, content: T): LazyCache<T> = TODO()
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
    * @throws IOException
    *
    * @throws ClassCastException
    *   when the [file] doesn't save an instance of [T]. In the strict sense,
    *   [ClassCastException] is thrown by a cast operation complemented by
    *   the compiler.
    *
    * @since 1.0.0
    */
   suspend fun get(time: Long = System.currentTimeMillis(),
                   accessCount: Float = .0f): T

   /**
    * returns the cached instance if it already exists on JVM memory, otherwise
    * returns `null`.
    * time and accessCount only affect when the cached instance is returned.
    *
    * @return the cached instance if it exists on JVM memory, otherwise `null`.
    * @throws ClassCastException
    * @since 1.0.0
    */
   fun getIfAlreadyLoaded(time: Long = System.currentTimeMillis(),
                          accessCount: Float = .0f): T?

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
    * converts this LazyCache to [Cache].
    * @since 1.0.0
    */
   suspend fun toCache(): Cache<T>

   /**
    * converts this LazyCache to [WeakCache].
    * @since 1.0.0
    */
   fun toWeakCache(): WeakCache<T>
}

/**
 * Writable LazyCache. The name says it all.
 * @since 1.0.0
 */
interface WritableLazyCache<T> : LazyCache<T> {
   companion object {
      /**
       * creates a new instance of WritableLazyCache.
       *
       * This may affect other instances that are associated with the same file,
       * in the same way as [WritableLazyCache.save]. So *this function has a
       * side effect.*
       *
       * @since 1.0.0
       */
      operator fun <T> invoke(file: File, content: T): WritableLazyCache<T> = TODO()
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
    * converts this WritableLazyCache to [WritableCache].
    * @since 1.0.0
    */
   override suspend fun toCache(): Cache<T>

   /**
    * converts this WritableLazyCache to [WritableWeakCache].
    * @since 1.0.0
    */
   override fun toWeakCache(): WeakCache<T>
}
