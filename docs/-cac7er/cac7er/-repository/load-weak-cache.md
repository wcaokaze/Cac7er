[Cac7er](../../index.md) / [cac7er](../index.md) / [Repository](index.md) / [loadWeakCache](./load-weak-cache.md)

# loadWeakCache

`abstract suspend fun loadWeakCache(key: `[`K`](index.md#K)`): `[`WeakCache`](../-weak-cache/index.md)`<`[`V`](index.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Repository.kt#L32)

loads a [WeakCache](../-weak-cache/index.md).

NOTE:
Even if the file doesn't exist, a [WeakCache](../-weak-cache/index.md) can be returned as
a representation that the cache was deleted by [GC](../-cac7er/gc.md).

### Exceptions

`IOException` -

**Since**
1.0.0

