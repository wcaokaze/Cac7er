[Cac7er](../../index.md) / [cac7er](../index.md) / [LazyCache](index.md) / [addObserver](./add-observer.md)

# addObserver

`abstract fun addObserver(observer: (`[`Cache`](../-cache/index.md)`<`[`T`](index.md#T)`>, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/LazyCache.kt#L71)

adds a function to observe this cache. Note that observers are referenced
as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC,
the observer instance should be owned by any other instance. The easiest
way is using [addObserver(Any, (Cache, T) -&gt; Unit)](./add-observer.md).

The observer also will be called when [get](get.md) has been completed to load the
content.

**Since**
0.3.0

`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, observer: (`[`Cache`](../-cache/index.md)`<`[`T`](index.md#T)`>, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/LazyCache.kt#L80)

As mentioned in another overload, observers are referenced as
[WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the
specified owner instance, and can observe until the owner is GCed.

**Since**
0.3.0

