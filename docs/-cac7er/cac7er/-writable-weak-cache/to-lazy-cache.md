[Cac7er](../../index.md) / [cac7er](../index.md) / [WritableWeakCache](index.md) / [toLazyCache](./to-lazy-cache.md)

# toLazyCache

`abstract fun toLazyCache(): `[`WritableLazyCache`](../-writable-lazy-cache/index.md)`<`[`T`](index.md#T)`>?` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/WeakCache.kt#L124)

Overrides [WeakCache.toLazyCache](../-weak-cache/to-lazy-cache.md)

converts this WritableWeakCache to [WritableLazyCache](../-writable-lazy-cache/index.md).

**Return**
WritableLazyCache or null if the cache was deleted by GC.

**Since**
0.1.0

