[Cac7er](../../index.md) / [cac7er](../index.md) / [Cache](./index.md)

# Cache

`interface Cache<out T>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cache.kt#L15)

The interface between a Kotlin instance and a file.

NOTE: It looks like immutable but actually not. [save](../-writable-repository/save.md)
or other functions may replace the instance.

### Parameters

`T` - The type of the cached instance.

**See Also**

[Cac7er](../-cac7er/index.md)

**Since**
0.1.0

### Functions

| Name | Summary |
|---|---|
| [addObserver](add-observer.md) | `abstract fun addObserver(observer: (`[`Cache`](./index.md)`<`[`T`](index.md#T)`>, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>adds a function to observe this cache. Note that observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC, the observer instance should be owned by any other instance. The easiest way is using [addObserver(Any, (Cache, T) -&gt; Unit)](add-observer.md).`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, observer: (`[`Cache`](./index.md)`<`[`T`](index.md#T)`>, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>As mentioned in another overload, observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the specified owner instance, and can observe until the owner is GCed. |
| [get](get.md) | `abstract fun get(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = 0.0f): `[`T`](index.md#T)<br>`open fun get(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = 0.0f): `[`T`](index.md#T) |
| [removeObserver](remove-observer.md) | `abstract fun removeObserver(observer: (`[`Cache`](./index.md)`<`[`T`](index.md#T)`>, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>removes the observer. The name says it all. |
| [toLazyCache](to-lazy-cache.md) | `abstract fun toLazyCache(): `[`LazyCache`](../-lazy-cache/index.md)`<`[`T`](index.md#T)`>`<br>converts this Cache to [LazyCache](../-lazy-cache/index.md). |
| [toWeakCache](to-weak-cache.md) | `abstract fun toWeakCache(): `[`WeakCache`](../-weak-cache/index.md)`<`[`T`](index.md#T)`>`<br>converts this Cache to [WeakCache](../-weak-cache/index.md). |

### Inheritors

| Name | Summary |
|---|---|
| [WritableCache](../-writable-cache/index.md) | `interface WritableCache<T> : `[`Cache`](./index.md)`<`[`T`](../-writable-cache/index.md#T)`>`<br>Writable Cache. The name says it all. |
