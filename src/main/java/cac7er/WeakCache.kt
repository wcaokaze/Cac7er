package cac7er

import java.io.*
import java.lang.ref.*

/**
 * Cache which doesn't depend on the content. See [Cac7er.gc] first.
 *
 * ```kotlin
 * class A
 * class B(val aCache: WeakCache<A>)
 * //                  ^^^^
 *
 * val aCache = WeakCache(File("~/.foo/cache/a"), A())
 * val bCache = Cache    (File("~/.foo/cache/b"), B(aCache))
 *
 * // ...
 *
 * Cac7er.gc(arrayOf(File("~/.foo/cache/a"),
 *                   File("~/.foo/cache/b")), 100L)
 * ```
 *
 * Then, [gc][Cac7er.gc] doesn't mind relationship, does delete `~/.foo/cache/a`
 * freely.
 *
 * When an instance of WeakCache is de-serialized, it doesn't have the content.
 * Whenever consumers get the content, they have to call a suspend function
 * [get]. Then WeakCache stores the content in a field as
 * [SoftReference]. The next invocation may suspend, or it may not.
 *
 * @since 1.0.0
 */
interface WeakCache<out T> {
   companion object {
      /**
       * creates a new instance of WeakCache.
       *
       * This may affect other instances that are associated with the same file,
       * in the same way as [WritableWeakCache.save]. So *this function has a
       * side effect.*
       *
       * @since 1.0.0
       */
      operator fun <T> invoke(file: File, content: T): WeakCache<T> = TODO()
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
    * @return
    *   the cached instance. Or `null` if the cache file was deleted by
    *   [gc][Cac7er.gc]
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
                   accessCount: Float = .0f): T?

   /**
    * adds a function to observe this cache. But WeakCache may deleted by GC.
    * Then observers are also deleted. Don't rely on it.
    *
    * @since 1.0.0
    */
   fun addObserver(observer: (T) -> Unit)

   /** @since 1.0.0 */
   fun addObserver(owner: Any, observer: (T) -> Unit)

   /** @since 1.0.0 */
   fun removeObserver(observer: (T) -> Unit)

   /**
    * converts this WeakCache to [Cache].
    * @since 1.0.0
    */
   suspend fun toCache(): Cache<T>

   /**
    * converts this WeakCache to [LazyCache].
    * @since 1.0.0
    */
   fun toLazyCache(): LazyCache<T>
}

/**
 * Writable WeakCache. The name says it all.
 * @since 1.0.0
 */
interface WritableWeakCache<T> : WeakCache<T> {
   companion object {
      /**
       * creates a new instance of WritableWeakCache.
       *
       * This may affect other instances that are associated with the same file,
       * in the same way as [WritableWeakCache.save]. So *this function has a
       * side effect.*
       *
       * @since 1.0.0
       */
      operator fun <T> invoke(file: File, content: T): WritableWeakCache<T> = TODO()
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
    * converts this WritableWeakCache to [WritableCache].
    * @since 1.0.0
    */
   override suspend fun toCache(): Cache<T>

   /**
    * converts this WritableWeakCache to [WritableLazyCache].
    * @since 1.0.0
    */
   override fun toLazyCache(): LazyCache<T>
}
