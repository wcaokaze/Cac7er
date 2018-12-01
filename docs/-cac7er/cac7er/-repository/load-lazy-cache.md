[Cac7er](../../index.md) / [cac7er](../index.md) / [Repository](index.md) / [loadLazyCache](./load-lazy-cache.md)

# loadLazyCache

`abstract fun loadLazyCache(key: `[`K`](index.md#K)`): `[`LazyCache`](../-lazy-cache/index.md)`<`[`V`](index.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Repository.kt#L40)

returns a [LazyCache](../-lazy-cache/index.md). This is not a suspend function since loading is
lazy (see [LazyCache.get](../-lazy-cache/get.md)).

**Since**
0.1.0

