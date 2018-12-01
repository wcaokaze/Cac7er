[Cac7er](../index.md) / [cac7er](index.md) / [loadOrCancel](./load-or-cancel.md)

# loadOrCancel

`suspend fun <K, V> `[`Repository`](-repository/index.md)`<`[`K`](load-or-cancel.md#K)`, `[`V`](load-or-cancel.md#V)`>.loadOrCancel(key: `[`K`](load-or-cancel.md#K)`): `[`Cache`](-cache/index.md)`<`[`V`](load-or-cancel.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L33)
`suspend fun <K, V> `[`WritableRepository`](-writable-repository/index.md)`<`[`K`](load-or-cancel.md#K)`, `[`V`](load-or-cancel.md#V)`>.loadOrCancel(key: `[`K`](load-or-cancel.md#K)`): `[`WritableCache`](-writable-cache/index.md)`<`[`V`](load-or-cancel.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L45)

loads a [Cache](-cache/index.md) or throws [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) if failed

### Exceptions

`CancellationException` - when failed to load a [Cache](-cache/index.md)