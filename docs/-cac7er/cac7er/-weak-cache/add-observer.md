[Cac7er](../../index.md) / [cac7er](../index.md) / [WeakCache](index.md) / [addObserver](./add-observer.md)

# addObserver

`abstract fun addObserver(observer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/WeakCache.kt#L61)

adds a function to observe this cache. Note that observers are referenced
as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC,
the observer instance should be owned by any other instance. The easiest
way is using [addObserver(Any, (T) -&gt; Unit)](./add-observer.md).

**Since**
0.1.0

`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, observer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/WeakCache.kt#L70)

As mentioned in another overload, observers are referenced as
[WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the
specified owner instance, and can observe until the owner is GCed.

**Since**
0.1.0

