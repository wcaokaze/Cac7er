[Cac7er](../../index.md) / [cac7er](../index.md) / [LazyCache](index.md) / [get](./get.md)

# get

`abstract suspend fun get(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](index.md#T) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/LazyCache.kt#L36)

### Parameters

`time` - the time when the Cache is accessed. [Cac7er.gc](../-cac7er/gc.md) makes this a criteria
to consider how the Cache is important.

`accessCount` - the number of access. The type is [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) for flexibility, infinite and
negative values also work well if you can utilize it. But `NaN` is
invalid (will be ignored).

### Exceptions

`IOException` -

`ClassCastException` - when the cached instance is not an instance of [T](index.md#T). In the strict sense,
[ClassCastException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-class-cast-exception/index.html) is thrown by a cast operation complemented by
the compiler.

**Return**
the cached instance

**Since**
0.1.0

`open suspend fun get(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](index.md#T) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/LazyCache.kt#L38)