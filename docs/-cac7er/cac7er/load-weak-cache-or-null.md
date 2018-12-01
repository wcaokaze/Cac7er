[Cac7er](../index.md) / [cac7er](index.md) / [loadWeakCacheOrNull](./load-weak-cache-or-null.md)

# loadWeakCacheOrNull

`suspend fun <K, V> `[`Repository`](-repository/index.md)`<`[`K`](load-weak-cache-or-null.md#K)`, `[`V`](load-weak-cache-or-null.md#V)`>.loadWeakCacheOrNull(key: `[`K`](load-weak-cache-or-null.md#K)`): `[`WeakCache`](-weak-cache/index.md)`<`[`V`](load-weak-cache-or-null.md#V)`>?` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L64)
`suspend fun <K, V> `[`WritableRepository`](-writable-repository/index.md)`<`[`K`](load-weak-cache-or-null.md#K)`, `[`V`](load-weak-cache-or-null.md#V)`>.loadWeakCacheOrNull(key: `[`K`](load-weak-cache-or-null.md#K)`): `[`WritableWeakCache`](-writable-weak-cache/index.md)`<`[`V`](load-weak-cache-or-null.md#V)`>?` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L78)

loads a [WeakCache](-weak-cache/index.md) or returns null if failed

**Return**
a [WeakCache](-weak-cache/index.md) or null

**Since**
0.1.0

