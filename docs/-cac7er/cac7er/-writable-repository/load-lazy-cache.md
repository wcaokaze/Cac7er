[Cac7er](../../index.md) / [cac7er](../index.md) / [WritableRepository](index.md) / [loadLazyCache](./load-lazy-cache.md)

# loadLazyCache

`abstract fun loadLazyCache(key: `[`K`](index.md#K)`): `[`WritableLazyCache`](../-writable-lazy-cache/index.md)`<`[`V`](index.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Repository.kt#L75)

Overrides [Repository.loadLazyCache](../-repository/load-lazy-cache.md)

returns a [LazyCache](../-lazy-cache/index.md). This is not a suspend function since loading is
lazy (see [LazyCache.get](../-lazy-cache/get.md)).

**Since**
1.0.0

