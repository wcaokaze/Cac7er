[Cac7er](../../index.md) / [cac7er](../index.md) / [WritableWeakCache](./index.md)

# WritableWeakCache

`interface WritableWeakCache<T> : `[`WeakCache`](../-weak-cache/index.md)`<`[`T`](index.md#T)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/WeakCache.kt#L99)

Writable WeakCache. The name says it all.

**Since**
0.1.0

### Functions

| Name | Summary |
|---|---|
| [save](save.md) | `abstract fun save(content: `[`T`](index.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>caches the new instance. |
| [toCache](to-cache.md) | `abstract fun toCache(): `[`WritableCache`](../-writable-cache/index.md)`<`[`T`](index.md#T)`>?`<br>converts this WritableWeakCache to [WritableCache](../-writable-cache/index.md). |
| [toLazyCache](to-lazy-cache.md) | `abstract fun toLazyCache(): `[`WritableLazyCache`](../-writable-lazy-cache/index.md)`<`[`T`](index.md#T)`>?`<br>converts this WritableWeakCache to [WritableLazyCache](../-writable-lazy-cache/index.md). |

### Inherited Functions

| Name | Summary |
|---|---|
| [addObserver](../-weak-cache/add-observer.md) | `abstract fun addObserver(observer: (`[`Cache`](../-cache/index.md)`<`[`T`](../-weak-cache/index.md#T)`>, `[`T`](../-weak-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>adds a function to observe this cache. Note that observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC, the observer instance should be owned by any other instance. The easiest way is using [addObserver(Any, (Cache, T) -&gt; Unit)](../-weak-cache/add-observer.md).`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, observer: (`[`Cache`](../-cache/index.md)`<`[`T`](../-weak-cache/index.md#T)`>, `[`T`](../-weak-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>As mentioned in another overload, observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the specified owner instance, and can observe until the owner is GCed. |
| [get](../-weak-cache/get.md) | `abstract fun get(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = 0.0f): `[`T`](../-weak-cache/index.md#T)`?`<br>`open fun get(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = 0.0f): `[`T`](../-weak-cache/index.md#T)`?` |
| [removeObserver](../-weak-cache/remove-observer.md) | `abstract fun removeObserver(observer: (`[`Cache`](../-cache/index.md)`<`[`T`](../-weak-cache/index.md#T)`>, `[`T`](../-weak-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>removes the observer. The name says it all. |
