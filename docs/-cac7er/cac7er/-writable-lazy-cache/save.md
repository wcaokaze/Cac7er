[Cac7er](../../index.md) / [cac7er](../index.md) / [WritableLazyCache](index.md) / [save](./save.md)

# save

`abstract fun save(content: `[`T`](index.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/LazyCache.kt#L108)

caches the new instance.

This function replaces the instance on JVM memory and returns immediately.
Some other thread writes it into the file at some future time.

**Since**
0.1.0

