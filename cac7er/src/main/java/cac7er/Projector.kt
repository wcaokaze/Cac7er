package cac7er

import kotlinx.coroutines.*

@Suppress("FunctionName")
fun <T> CoroutineScope.Projector(onStartToLoadLazyCache: () -> Unit = {},
                                 onFailedToLoadLazyCache: (Exception) -> Unit = {},
                                 onSetDeletedCache: (WeakCache<T>) -> Unit = {},
                                 onUnset: () -> Unit = {},
                                 onCacheUpdate: (T) -> Unit): Projector<T>
{
   return Projector(this,
         onStartToLoadLazyCache, onFailedToLoadLazyCache,
         onSetDeletedCache, onUnset, onCacheUpdate)
}

/**
 * In most cases, onStartToLoadLazyCache and onFailedToLoadLazyCache and
 * onSetDeletedCache are same function.
 *
 * ```kotlin
 * private val projector = Projector<User>(
 *    onNull    = { usernameView.text = ""          },
 *    onContent = { usernameView.text = it.username }
 * )
 * ```
 *
 * @param onNull
 *   called when [LazyCache] attempts to load the content (onStartToLoadLazyCache),
 *   when [LazyCache] failed to load the content (onFailedToLoadLazyCache),
 *   when set [WeakCache] is already deleted by GC (onSetDeletedCache),
 *   when the new content is null.
 *
 * @param onContent
 *   called when the [Cache] has been set a new non-null content.
 *
 * @since 0.4.0
 */
@Suppress("FunctionName")
fun <T> CoroutineScope.Projector(onNull: () -> Unit,
                                 onContent: (T) -> Unit): Projector<T?>
{
   return Projector(this,
         onStartToLoadLazyCache = onNull,
         onFailedToLoadLazyCache = { onNull() },
         onSetDeletedCache = { onNull() },
         onUnset = { onNull() },
         onCacheUpdate = {
            if (it == null) {
               onNull()
            } else {
               onContent(it)
            }
         })
}

/**
 * Projects a [Cache] into a View.
 *
 * ```kotlin
 * private val projector = Projector<User> {
 *    usernameView  .text = it.username
 *    screenNameView.text = it.screenName
 * }
 *
 * fun setUser(userCache: Cache<User>) {
 *    projector.setCache(userCache, accessCount = 1.0f)
 * }
 * ```
 * When the cache is updated, The Projector reruns its lambda. `accessCount` is
 * increased on the first time (in [setCache]).
 *
 * Notice that `projector` is a property, not a local variable. This is
 * to avoid GC. Projectors can be deleted by GC, and if deleted, it cannot
 * observe the cache.
 *
 * As you know, Cac7er provides three types of cache;
 * [Cache], [WeakCache], [LazyCache]. Projector is adaptable to all of them.
 *
 *
 * #### RecyclerView (Android)
 *
 * ViewHolders are recycled but it's alright for Projector.
 * ```kotlin
 * class UserViewHolder(context: Context)
 *       : RecyclerView.ViewHolder(LinearLayout(context))
 * {
 *    private val usernameView: TextView
 *
 *    private val projector = Projector<User> {
 *       usernameView.text = it.username
 *    }
 *
 *    fun bind(userCache: Cache<User>) {
 *       projector.setCache(userCache, accessCount = 1.0f)
 *    }
 *
 *    init {
 *       ko5hian(itemView as LinearLayout) {
 *          layout.width  = MATCH_PARENT
 *          layout.height = WRAP_CONTENT
 *          view.orientation = VERTICAL
 *
 *          usernameView = textView {
 *             layout.width  = WRAP_CONTENT
 *             layout.height = WRAP_CONTENT
 *          }
 *       }
 *    }
 * }
 * ```
 *
 *
 * @param onStartToLoadLazyCache
 *   will be invoked when [setLazyCache] attempts to load the file.
 *   Typically this function clears the view which is showing another
 *   Cache contents; since the view is a child of RecyclerView.
 *
 * @param onFailedToLoadLazyCache
 *   will be invoked when [setLazyCache] fails to load the file.
 *
 * @param onSetDeletedCache
 *   will be invoked when [setWeakCache] attempts to set a WeakCache which is already
 *   deleted by GC.
 *
 * @param onUnset
 *   will be invoked when [unset] is called.
 *
 * @param onCacheUpdate
 *   will be invoked when the content of [Cache] is rewritten.
 *
 * @since 0.4.0
 */
class Projector<T>(private val coroutineScope: CoroutineScope,
                   private val onStartToLoadLazyCache: () -> Unit = {},
                   private val onFailedToLoadLazyCache: (Exception) -> Unit = {},
                   private val onSetDeletedCache: (WeakCache<T>) -> Unit = {},
                   private val onUnset: () -> Unit = {},
                   private val onCacheUpdate: (T) -> Unit)
{
   private val observer = fun (cache: Cache<T>, cacheContent: T) {
      if (cache !is CacheImpl) { return }

      coroutineScope.launch(Dispatchers.Main) {
         // between launch invocation and coroutine running, cache can be restored.
         // synchronized is not necessary since all accessors for cache run on the main thread.
         if (cache.uniformizer !== uniformizer) { throw CancellationException() }

         onCacheUpdate(cacheContent)
      }
   }

   private var uniformizer: Uniformizer<T>? = null

   /*
   val cache: Cache<T>?
      get() = uniformizer?.let { CacheImpl(it) }
      */

   /**
    * unset the [Cache] (or [WeakCache], [LazyCache]).
    *
    * @since 0.5.0
    */
   fun unset() {
      uniformizer?.removeObserver(observer)
      uniformizer = null
      onUnset()
   }

   /**
    * set an instance.
    *
    * Of course this cannot observe any [Cache]. Projector will call
    * onCacheUpdate exactly once and will never call any more, until this
    * Projector is set another [Cache].
    *
    * @since 0.6.0
    */
   fun setStatic(content: T) {
      if (uniformizer != null) {
         uniformizer?.removeObserver(observer)
         uniformizer = null
      }

      onCacheUpdate(content)
   }

   /**
    * set a [Cache] to this projector.
    *
    * This must be invoked on the UI thread.
    *
    * This will invoke onCacheUpdate to project the cache, and will increase
    * accessCount of the [Cache] is increased immediately.
    *
    * At some future time, the cache being updated,
    * onCacheUpdate will be invoked again but it will not affect accessCount.
    * accessCount increases only for the first time.
    *
    * @since 0.4.0
    */
   fun setCache(cache: Cache<T>, accessCount: Float) {
      if (cache !is CacheImpl) {
         throw IllegalArgumentException("Don't use another Cache implementation.")
      }

      val uniformizer = cache.uniformizer

      if (uniformizer === this.uniformizer) { return }

      this.uniformizer?.removeObserver(observer)

      onCacheUpdate(cache.get(accessCount))

      this.uniformizer = uniformizer
      uniformizer.addObserver(observer)
   }

   /**
    * set a [LazyCache] to this projector.
    *
    * Almost same as [setCache], except that this function may load the LazyCache
    * if necessary.
    *
    * @since 0.4.0
    */
   fun setLazyCache(lazyCache: LazyCache<T>, accessCount: Float) {
      if (lazyCache !is LazyCacheImpl) {
         throw IllegalArgumentException("Don't use another LazyCache implementation.")
      }

      coroutineScope.launch(Dispatchers.Main, CoroutineStart.UNDISPATCHED) {
         val uniformizer = lazyCache.uniformizer

         if (uniformizer === this@Projector.uniformizer) { return@launch }

         this@Projector.uniformizer?.removeObserver(observer)

         var value = lazyCache.getIfAlreadyLoaded(accessCount = accessCount)

         if (value == null) {
            onStartToLoadLazyCache()

            try {
               value = lazyCache.get(accessCount)
            } catch (e: Exception) {
               onFailedToLoadLazyCache(e)
               throw CancellationException()
            }
         }

         onCacheUpdate(value!!)

         this@Projector.uniformizer = uniformizer
         uniformizer.addObserver(observer)
      }
   }

   /**
    * set a [WeakCache] to this projector.
    *
    * Almost same as [setCache].
    *
    * @since 0.4.0
    */
   fun setWeakCache(weakCache: WeakCache<T>, accessCount: Float) {
      if (weakCache !is WeakCacheImpl) {
         throw IllegalArgumentException("Don't use another WeakCache implementation.")
      }

      val uniformizer = weakCache.uniformizer

      if (uniformizer === this.uniformizer) { return }

      this.uniformizer?.removeObserver(observer)

      val value = weakCache.get(accessCount)

      if (value == null) {
         onSetDeletedCache(weakCache)
      } else {
         onCacheUpdate(value)
      }

      this.uniformizer = uniformizer
      uniformizer.addObserver(observer)
   }

   /**
    * set a [FutureCache] to this projector.
    *
    * NOTE:
    * [FutureCache] doesn't provide any functions to load the content,
    * but this function loads the content if possible. This is similar to
    * [setLazyCache].
    *
    * @since 0.4.0
    */
   fun setFutureCache(futureCache: FutureCache<T>) {
      if (futureCache !is LazyCacheImpl) {
         throw IllegalArgumentException("Don't use another FutureCache implementation.")
      }

      coroutineScope.launch(Dispatchers.Main, CoroutineStart.UNDISPATCHED) {
         val uniformizer = futureCache.uniformizer

         if (uniformizer === this@Projector.uniformizer) { return@launch }

         this@Projector.uniformizer?.removeObserver(observer)

         var value = futureCache.getIfAlreadyLoaded()

         if (value == null) {
            onStartToLoadLazyCache()

            value = futureCache.getOrNull()

            if (value == null) {
               onUnset()
            }
         }

         onCacheUpdate(value!!)

         this@Projector.uniformizer = uniformizer
         uniformizer.addObserver(observer)
      }
   }
}
