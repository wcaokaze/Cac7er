[Cac7er](../../index.md) / [cac7er](../index.md) / [WritableCache](./index.md)

# WritableCache

`interface WritableCache<T> : `[`Cache`](../-cache/index.md)`<`[`T`](index.md#T)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cache.kt#L82)

Writable Cache. The name says it all.

**Since**
0.1.0

### Functions

| Name | Summary |
|---|---|
| [save](save.md) | `abstract fun save(content: `[`T`](index.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>caches the new instance. |
| [toLazyCache](to-lazy-cache.md) | `abstract fun toLazyCache(): `[`WritableLazyCache`](../-writable-lazy-cache/index.md)`<`[`T`](index.md#T)`>`<br>converts this WritableCache to [WritableLazyCache](../-writable-lazy-cache/index.md). |
| [toWeakCache](to-weak-cache.md) | `abstract fun toWeakCache(): `[`WritableWeakCache`](../-writable-weak-cache/index.md)`<`[`T`](index.md#T)`>`<br>converts this WritableCache to [WritableWeakCache](../-writable-weak-cache/index.md). |

### Inherited Functions

| Name | Summary |
|---|---|
| [addObserver](../-cache/add-observer.md) | `abstract fun addObserver(observer: (`[`T`](../-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>adds a function to observe this cache. Note that observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC, the observer instance should be owned by any other instance. The easiest way is using [addObserver(Any, (T) -&gt; Unit)](../-cache/add-observer.md).`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, observer: (`[`T`](../-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>As mentioned in another overload, observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the specified owner instance, and can observe until the owner is GCed. |
| [get](../-cache/get.md) | `abstract fun get(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../-cache/index.md#T)<br>`open fun get(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../-cache/index.md#T) |
| [removeObserver](../-cache/remove-observer.md) | `abstract fun removeObserver(observer: (`[`T`](../-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>removes the observer. The name says it all. |
