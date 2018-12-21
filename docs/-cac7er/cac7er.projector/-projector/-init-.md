[Cac7er](../../index.md) / [cac7er.projector](../index.md) / [Projector](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`Projector(coroutineScope: `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`, onStartToLoadLazyCache: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onFailedToLoadLazyCache: (`[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onSetDeletedCache: (`[`WeakCache`](../../cac7er/-weak-cache/index.md)`<`[`T`](index.md#T)`>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onCacheUpdate: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`

Projects a [Cache](../../cac7er/-cache/index.md) into a View.

``` kotlin
private val projector = Projector<User> {
   usernameView  .text = it.username
   screenNameView.text = it.screenName
}

fun setUser(userCache: Cache<User>) {
   projector.setCache(userCache, accessCount = 1.0f)
}
```

When the cache is updated, The Projector reruns its lambda. `accessCount` is
increased on the first time (in [setCache](set-cache.md)).

Notice that `projector` is a property, not a local variable. This is
to avoid GC. Projectors can be deleted by GC, and if deleted, it cannot
observe the cache.

As you know, Cac7er provides three types of cache;
[Cache](../../cac7er/-cache/index.md), [WeakCache](../../cac7er/-weak-cache/index.md), [LazyCache](../../cac7er/-lazy-cache/index.md). Projector is adaptable to all of them.

#### RecyclerView (Android)

ViewHolders are recycled but it's alright for Projector.

``` kotlin
class UserViewHolder(context: Context)
      : RecyclerView.ViewHolder(LinearLayout(context))
{
   private val usernameView: TextView

   private val projector = Projector<User> {
      usernameView.text = it.username
   }

   fun bind(userCache: Cache<User>) {
      projector.setCache(userCache, accessCount = 1.0f)
   }

   init {
      ko5hian(itemView as LinearLayout) {
         layout.width  = MATCH_PARENT
         layout.height = WRAP_CONTENT
         view.orientation = VERTICAL

         usernameView = textView {
            layout.width  = WRAP_CONTENT
            layout.height = WRAP_CONTENT
         }
      }
   }
}
```

### Parameters

`onStartToLoadLazyCache` - invoked when [setLazyCache](set-lazy-cache.md) attempts to load the file.
Typically this function clears the view which is showing another
Cache contents; since the view is a child of RecyclerView.

`onFailedToLoadLazyCache` - invoked when [setLazyCache](set-lazy-cache.md) fails to load the file.

`onSetDeletedCache` - invoked when [setWeakCache](set-weak-cache.md) attempts to set a WeakCache which is already
deleted by GC.

`onCacheUpdate` - invoked when the content of [Cache](../../cac7er/-cache/index.md) is rewritten.