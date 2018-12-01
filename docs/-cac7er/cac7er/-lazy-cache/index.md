[Cac7er](../../index.md) / [cac7er](../index.md) / [LazyCache](./index.md)

# LazyCache

`interface LazyCache<out T>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/LazyCache.kt#L14)

Cache which doesn't load the content until calling [get](get.md).

The content is stored as [SoftReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/SoftReference.html), whenever consumers get the content,
they have to call a suspend function.

**Since**
0.1.0

### Functions

| Name | Summary |
|---|---|
| [addObserver](add-observer.md) | `abstract fun addObserver(observer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>adds a function to observe this cache. Note that observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC, the observer instance should be owned by any other instance. The easiest way is using [addObserver(Any, (T) -&gt; Unit)](add-observer.md).`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, observer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>As mentioned in another overload, observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the specified owner instance, and can observe until the owner is GCed. |
| [get](get.md) | `abstract suspend fun get(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](index.md#T)<br>`open suspend fun get(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](index.md#T) |
| [getIfAlreadyLoaded](get-if-already-loaded.md) | `abstract fun getIfAlreadyLoaded(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = System.currentTimeMillis(), accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](index.md#T)`?`<br>returns the cached instance if it already exists on JVM memory, otherwise returns `null`. |
| [removeObserver](remove-observer.md) | `abstract fun removeObserver(observer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>removes the observer. The name says it all. |
| [toCache](to-cache.md) | `abstract suspend fun toCache(): `[`Cache`](../-cache/index.md)`<`[`T`](index.md#T)`>`<br>converts this LazyCache to [Cache](../-cache/index.md). |
| [toWeakCache](to-weak-cache.md) | `abstract suspend fun toWeakCache(): `[`WeakCache`](../-weak-cache/index.md)`<`[`T`](index.md#T)`>`<br>converts this LazyCache to [WeakCache](../-weak-cache/index.md). |

### Extension Functions

| Name | Summary |
|---|---|
| [getOrCancel](../get-or-cancel.md) | `suspend fun <T> `[`LazyCache`](./index.md)`<`[`T`](../get-or-cancel.md#T)`>.getOrCancel(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../get-or-cancel.md#T)<br>loads the content or throws [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) if failed |
| [getOrNull](../get-or-null.md) | `suspend fun <T> `[`LazyCache`](./index.md)`<`[`T`](../get-or-null.md#T)`>.getOrNull(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](../get-or-null.md#T)`?`<br>loads the content or returns null if failed |

### Inheritors

| Name | Summary |
|---|---|
| [WritableLazyCache](../-writable-lazy-cache/index.md) | `interface WritableLazyCache<T> : `[`LazyCache`](./index.md)`<`[`T`](../-writable-lazy-cache/index.md#T)`>`<br>Writable LazyCache. The name says it all. |
