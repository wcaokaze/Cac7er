[Cac7er](../../index.md) / [cac7er.projector](../index.md) / [Projector](index.md) / [setLazyCache](./set-lazy-cache.md)

# setLazyCache

`fun setLazyCache(lazyCache: `[`LazyCache`](../../cac7er/-lazy-cache/index.md)`<`[`T`](index.md#T)`>, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/projector/Projector.kt#L174)

set a [LazyCache](../../cac7er/-lazy-cache/index.md) to this projector.

Almost same as [setCache](set-cache.md), except that this function may load the LazyCache
if necessary.

