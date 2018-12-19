[Cac7er](../../index.md) / [cac7er](../index.md) / [LazyCache](index.md) / [getIfAlreadyLoaded](./get-if-already-loaded.md)

# getIfAlreadyLoaded

`abstract fun getIfAlreadyLoaded(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = System.currentTimeMillis(), accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = 0.0f): `[`T`](index.md#T)`?` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/LazyCache.kt#L57)

returns the cached instance if it already exists on JVM memory, otherwise
returns `null`.

time and accessCount only affect when the cached instance is returned.

### Exceptions

`ClassCastException` -

**Return**
the cached instance if it exists on JVM memory, otherwise `null`.

**Since**
0.1.0

