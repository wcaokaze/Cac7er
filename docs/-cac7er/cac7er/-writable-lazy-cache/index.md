[Cac7er](../../index.md) / [cac7er](../index.md) / [WritableLazyCache](./index.md)

# WritableLazyCache

`interface WritableLazyCache<T> : `[`LazyCache`](../-lazy-cache/index.md)`<`[`T`](index.md#T)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/LazyCache.kt#L99)

Writable LazyCache. The name says it all.

**Since**
0.1.0

### Functions

| Name | Summary |
|---|---|
| [save](save.md) | `abstract fun save(content: `[`T`](index.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>caches the new instance. |
| [toCache](to-cache.md) | `abstract suspend fun toCache(): `[`WritableCache`](../-writable-cache/index.md)`<`[`T`](index.md#T)`>`<br>converts this WritableLazyCache to [WritableCache](../-writable-cache/index.md). |
| [toWeakCache](to-weak-cache.md) | `abstract suspend fun toWeakCache(): `[`WritableWeakCache`](../-writable-weak-cache/index.md)`<`[`T`](index.md#T)`>`<br>converts this WritableLazyCache to [WritableWeakCache](../-writable-weak-cache/index.md). |

### Inherited Functions

| Name | Summary |
|---|---|
| [addObserver](../-lazy-cache/add-observer.md) | `abstract fun addObserver(observer: (`[`T`](../-lazy-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>adds a function to observe this cache. Note that observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC, the observer instance should be owned by any other instance. The easiest way is using [addObserver(Any, (T) -&gt; Unit)](../-lazy-cache/add-observer.md).`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, observer: (`[`T`](../-lazy-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>As mentioned in another overload, observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the specified owner instance, and can observe until the owner is GCed. |
| [get](../-lazy-cache/get.md) | `abstract suspend fun get(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../-lazy-cache/index.md#T)<br>`open suspend fun get(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../-lazy-cache/index.md#T) |
| [getIfAlreadyLoaded](../-lazy-cache/get-if-already-loaded.md) | `abstract fun getIfAlreadyLoaded(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = System.currentTimeMillis(), accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../-lazy-cache/index.md#T)`?`<br>returns the cached instance if it already exists on JVM memory, otherwise returns `null`. |
| [removeObserver](../-lazy-cache/remove-observer.md) | `abstract fun removeObserver(observer: (`[`T`](../-lazy-cache/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>removes the observer. The name says it all. |

### Extension Functions

| Name | Summary |
|---|---|
| [getOrCancel](../get-or-cancel.md) | `suspend fun <T> `[`LazyCache`](../-lazy-cache/index.md)`<`[`T`](../get-or-cancel.md#T)`>.getOrCancel(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../get-or-cancel.md#T)<br>loads the content or throws [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) if failed |
| [getOrNull](../get-or-null.md) | `suspend fun <T> `[`LazyCache`](../-lazy-cache/index.md)`<`[`T`](../get-or-null.md#T)`>.getOrNull(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../get-or-null.md#T)`?`<br>loads the content or returns null if failed |
