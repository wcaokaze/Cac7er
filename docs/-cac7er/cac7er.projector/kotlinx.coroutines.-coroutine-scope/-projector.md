[Cac7er](../../index.md) / [cac7er.projector](../index.md) / [kotlinx.coroutines.CoroutineScope](index.md) / [Projector](./-projector.md)

# Projector

`fun <T> `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.Projector(onStartToLoadLazyCache: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onFailedToLoadLazyCache: (`[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onSetDeletedCache: (`[`WeakCache`](../../cac7er/-weak-cache/index.md)`<`[`T`](-projector.md#T)`>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onCacheUpdate: (`[`T`](-projector.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Projector`](../-projector/index.md)`<`[`T`](-projector.md#T)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/projector/Projector.kt#L8)`fun <T> `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.Projector(onNull: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, onContent: (`[`T`](-projector.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Projector`](../-projector/index.md)`<`[`T`](-projector.md#T)`?>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/projector/Projector.kt#L41)

In most cases, onStartToLoadLazyCache and onFailedToLoadLazyCache and
onSetDeletedCache are same function.

``` kotlin
private val projector = Projector<User>(
   onNull    = { usernameView.text = ""          },
   onContent = { usernameView.text = it.username }
)
```

### Parameters

`onNull` - called when [LazyCache](../../cac7er/-lazy-cache/index.md) attempts to load the content (onStartToLoadLazyCache),
when [LazyCache](../../cac7er/-lazy-cache/index.md) failed to load the content (onFailedToLoadLazyCache),
when set [WeakCache](../../cac7er/-weak-cache/index.md) is already deleted by GC (onSetDeletedCache),
when the new content is null.

`onContent` - called when the [Cache](../../cac7er/-cache/index.md) has been set a new non-null content.

**Since**
0.4.0

