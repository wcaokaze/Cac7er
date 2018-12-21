package cac7er.projector

import cac7er.*

import kotlinx.coroutines.*

@Suppress("FunctionName")
fun <T> CoroutineScope.Projector(onStartToLoadLazyCache: () -> Unit = {},
                                 onFailedToLoadLazyCache: (Exception) -> Unit = {},
                                 onSetDeletedCache: (WeakCache<T>) -> Unit = {},
                                 onCacheUpdate: (T) -> Unit): Projector<T>
{
   return Projector(this,
      onStartToLoadLazyCache, onFailedToLoadLazyCache,
      onSetDeletedCache, onCacheUpdate)
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
      onStartToLoadLazyCache  = onNull,
      onFailedToLoadLazyCache = { onNull() },
      onSetDeletedCache       = { onNull() },
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
 *   invoked when [setLazyCache] attempts to load the file.
 *   Typically this function clears the view which is showing another
 *   Cache contents; since the view is a child of RecyclerView.
 *
 * @param onFailedToLoadLazyCache
 *   invoked when [setLazyCache] fails to load the file.
 *
 * @param onSetDeletedCache
 *   invoked when [setWeakCache] attempts to set a WeakCache which is already
 *   deleted by GC.
 *
 * @param onCacheUpdate
 *   invoked when the content of [Cache] is rewritten.
 */
class Projector<in T>(private val coroutineScope: CoroutineScope,
                      private val onStartToLoadLazyCache: () -> Unit = {},
                      private val onFailedToLoadLazyCache: (Exception) -> Unit = {},
                      private val onSetDeletedCache: (WeakCache<T>) -> Unit = {},
                      private val onCacheUpdate: (T) -> Unit)
{
   private val observer: (Cache<T>, T) -> Unit = { cache, cacheContent ->
      coroutineScope.launch(Dispatchers.Main) {
         // between launch invocation and coroutine running, _cache can be restored.
         // synchronized is not necessary since all accessors for _cache run on the main thread.
         if (cache != _cache) throw CancellationException()

         onCacheUpdate(cacheContent)
      }
   }

   private var _cache: Cache<T>? = null

   /**
    * set a [Cache] to this projector.
    *
    * This must be invoked on the UI thread.
    *
    * This will invoke [onCacheUpdate] to project the cache, and will increase
    * accessCount of the [Cache] is increased immediately.
    *
    * At some future time, the cache being updated,
    * [onCacheUpdate] will be invoked again but it will not affect accessCount.
    * accessCount increases only for the first time.
    */
   fun setCache(cache: Cache<T>, accessCount: Float) {
      _cache?.removeObserver(observer)

      onCacheUpdate(cache.get(accessCount))

      _cache = cache
      cache.addObserver(observer)
   }

   /**
    * set a [LazyCache] to this projector.
    *
    * Almost same as [setCache], except that this function may load the LazyCache
    * if necessary.
    */
   fun setLazyCache(lazyCache: LazyCache<T>, accessCount: Float) {
      coroutineScope.launch(Dispatchers.Main, CoroutineStart.UNDISPATCHED) {
         _cache?.removeObserver(observer)

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

         _cache = lazyCache.toCache()
         lazyCache.addObserver(observer)
      }
   }

   /**
    * set a [WeakCache] to this projector.
    *
    * Almost same as [setCache].
    */
   fun setWeakCache(weakCache: WeakCache<T>, accessCount: Float) {
      _cache?.removeObserver(observer)

      val value = weakCache.get(accessCount)

      if (value == null) {
         onSetDeletedCache(weakCache)
      } else {
         onCacheUpdate(value)
      }

      _cache = weakCache.toCache()
      weakCache.addObserver(observer)
   }
}
