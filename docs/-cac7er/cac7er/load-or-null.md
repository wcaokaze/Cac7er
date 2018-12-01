[Cac7er](../index.md) / [cac7er](index.md) / [loadOrNull](./load-or-null.md)

# loadOrNull

`suspend fun <K, V> `[`Repository`](-repository/index.md)`<`[`K`](load-or-null.md#K)`, `[`V`](load-or-null.md#V)`>.loadOrNull(key: `[`K`](load-or-null.md#K)`): `[`Cache`](-cache/index.md)`<`[`V`](load-or-null.md#V)`>?` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L9)
`suspend fun <K, V> `[`WritableRepository`](-writable-repository/index.md)`<`[`K`](load-or-null.md#K)`, `[`V`](load-or-null.md#V)`>.loadOrNull(key: `[`K`](load-or-null.md#K)`): `[`WritableCache`](-writable-cache/index.md)`<`[`V`](load-or-null.md#V)`>?` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/safeLoadFunctions.kt#L21)

loads a [Cache](-cache/index.md) or returns null if failed

**Return**
a [Cache](-cache/index.md) or null

