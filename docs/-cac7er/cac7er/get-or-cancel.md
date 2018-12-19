[Cac7er](../index.md) / [cac7er](index.md) / [getOrCancel](./get-or-cancel.md)

# getOrCancel

`suspend fun <T> `[`LazyCache`](-lazy-cache/index.md)`<`[`T`](get-or-cancel.md#T)`>.getOrCancel(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = 0.0f): `[`T`](get-or-cancel.md#T) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L135)

loads the content or throws [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) if failed

### Exceptions

`CancellationException` - when failed to load the content of LazyCache

**Since**
0.2.0

