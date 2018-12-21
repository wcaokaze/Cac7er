[Cac7er](../../index.md) / [cac7er.projector](../index.md) / [Projector](index.md) / [setCache](./set-cache.md)

# setCache

`fun setCache(cache: `[`Cache`](../../cac7er/-cache/index.md)`<`[`T`](index.md#T)`>, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/projector/Projector.kt#L159)

set a [Cache](../../cac7er/-cache/index.md) to this projector.

This must be invoked on the UI thread.

This will invoke onCacheUpdate to project the cache, and will increase
accessCount of the [Cache](../../cac7er/-cache/index.md) is increased immediately.

At some future time, the cache being updated,
onCacheUpdate will be invoked again but it will not affect accessCount.
accessCount increases only for the first time.

