[Cac7er](../index.md) / [cac7er](index.md) / [loadWeakCacheOrCancel](./load-weak-cache-or-cancel.md)

# loadWeakCacheOrCancel

`suspend fun <K, V> `[`Repository`](-repository/index.md)`<`[`K`](load-weak-cache-or-cancel.md#K)`, `[`V`](load-weak-cache-or-cancel.md#V)`>.loadWeakCacheOrCancel(key: `[`K`](load-weak-cache-or-cancel.md#K)`): `[`WeakCache`](-weak-cache/index.md)`<`[`V`](load-weak-cache-or-cancel.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L85)
`suspend fun <K, V> `[`WritableRepository`](-writable-repository/index.md)`<`[`K`](load-weak-cache-or-cancel.md#K)`, `[`V`](load-weak-cache-or-cancel.md#V)`>.loadWeakCacheOrCancel(key: `[`K`](load-weak-cache-or-cancel.md#K)`): `[`WritableWeakCache`](-writable-weak-cache/index.md)`<`[`V`](load-weak-cache-or-cancel.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L98)

loads a [WeakCache](-weak-cache/index.md) or throws [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) if failed

### Exceptions

`CancellationException` - when failed to load a [WeakCache](-weak-cache/index.md)