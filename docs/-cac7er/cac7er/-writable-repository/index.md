[Cac7er](../../index.md) / [cac7er](../index.md) / [WritableRepository](./index.md)

# WritableRepository

`interface WritableRepository<in K, V> : `[`Repository`](../-repository/index.md)`<`[`K`](index.md#K)`, `[`V`](index.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Repository.kt#L72)

Writable Repository. The name says it all.

**Since**
0.1.0

### Inherited Properties

| Name | Summary |
|---|---|
| [name](../-repository/name.md) | `abstract val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [load](load.md) | `abstract suspend fun load(key: `[`K`](index.md#K)`): `[`WritableCache`](../-writable-cache/index.md)`<`[`V`](index.md#V)`>`<br>loads a [Cache](../-cache/index.md). |
| [loadLazyCache](load-lazy-cache.md) | `abstract fun loadLazyCache(key: `[`K`](index.md#K)`): `[`WritableLazyCache`](../-writable-lazy-cache/index.md)`<`[`V`](index.md#V)`>`<br>returns a [LazyCache](../-lazy-cache/index.md). This is not a suspend function since loading is lazy (see [LazyCache.get](../-lazy-cache/get.md)). |
| [loadWeakCache](load-weak-cache.md) | `abstract suspend fun loadWeakCache(key: `[`K`](index.md#K)`): `[`WritableWeakCache`](../-writable-weak-cache/index.md)`<`[`V`](index.md#V)`>`<br>loads a [WeakCache](../-weak-cache/index.md). |
| [save](save.md) | `abstract fun save(key: `[`K`](index.md#K)`, value: `[`V`](index.md#V)`): `[`WritableCache`](../-writable-cache/index.md)`<`[`V`](index.md#V)`>`<br>saves a cache. |

### Inherited Functions

| Name | Summary |
|---|---|
| [addObserver](../-repository/add-observer.md) | `abstract fun addObserver(key: `[`K`](../-repository/index.md#K)`, observer: (`[`V`](../-repository/index.md#V)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>adds a function to observe a key. Note that observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC, the observer instance should be owned by any other instance. The easiest way is using [addObserver(Any, (T) -&gt; Unit)](../-repository/add-observer.md).`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, key: `[`K`](../-repository/index.md#K)`, observer: (`[`V`](../-repository/index.md#V)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>As mentioned in another overload, observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the specified owner instance, and can observe until the owner is GCed. |
| [removeObserver](../-repository/remove-observer.md) | `abstract fun removeObserver(key: `[`K`](../-repository/index.md#K)`, observer: (`[`V`](../-repository/index.md#V)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>removes the observer. The name says it all. |

### Extension Functions

| Name | Summary |
|---|---|
| [loadOrCancel](../load-or-cancel.md) | `suspend fun <K, V> `[`WritableRepository`](./index.md)`<`[`K`](../load-or-cancel.md#K)`, `[`V`](../load-or-cancel.md#V)`>.loadOrCancel(key: `[`K`](../load-or-cancel.md#K)`): `[`WritableCache`](../-writable-cache/index.md)`<`[`V`](../load-or-cancel.md#V)`>`<br>`suspend fun <K, V> `[`Repository`](../-repository/index.md)`<`[`K`](../load-or-cancel.md#K)`, `[`V`](../load-or-cancel.md#V)`>.loadOrCancel(key: `[`K`](../load-or-cancel.md#K)`): `[`Cache`](../-cache/index.md)`<`[`V`](../load-or-cancel.md#V)`>`<br>loads a [Cache](../-cache/index.md) or throws [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) if failed |
| [loadOrNull](../load-or-null.md) | `suspend fun <K, V> `[`WritableRepository`](./index.md)`<`[`K`](../load-or-null.md#K)`, `[`V`](../load-or-null.md#V)`>.loadOrNull(key: `[`K`](../load-or-null.md#K)`): `[`WritableCache`](../-writable-cache/index.md)`<`[`V`](../load-or-null.md#V)`>?`<br>`suspend fun <K, V> `[`Repository`](../-repository/index.md)`<`[`K`](../load-or-null.md#K)`, `[`V`](../load-or-null.md#V)`>.loadOrNull(key: `[`K`](../load-or-null.md#K)`): `[`Cache`](../-cache/index.md)`<`[`V`](../load-or-null.md#V)`>?`<br>loads a [Cache](../-cache/index.md) or returns null if failed |
| [loadWeakCacheOrCancel](../load-weak-cache-or-cancel.md) | `suspend fun <K, V> `[`WritableRepository`](./index.md)`<`[`K`](../load-weak-cache-or-cancel.md#K)`, `[`V`](../load-weak-cache-or-cancel.md#V)`>.loadWeakCacheOrCancel(key: `[`K`](../load-weak-cache-or-cancel.md#K)`): `[`WritableWeakCache`](../-writable-weak-cache/index.md)`<`[`V`](../load-weak-cache-or-cancel.md#V)`>`<br>`suspend fun <K, V> `[`Repository`](../-repository/index.md)`<`[`K`](../load-weak-cache-or-cancel.md#K)`, `[`V`](../load-weak-cache-or-cancel.md#V)`>.loadWeakCacheOrCancel(key: `[`K`](../load-weak-cache-or-cancel.md#K)`): `[`WeakCache`](../-weak-cache/index.md)`<`[`V`](../load-weak-cache-or-cancel.md#V)`>`<br>loads a [WeakCache](../-weak-cache/index.md) or throws [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) if failed |
| [loadWeakCacheOrNull](../load-weak-cache-or-null.md) | `suspend fun <K, V> `[`WritableRepository`](./index.md)`<`[`K`](../load-weak-cache-or-null.md#K)`, `[`V`](../load-weak-cache-or-null.md#V)`>.loadWeakCacheOrNull(key: `[`K`](../load-weak-cache-or-null.md#K)`): `[`WritableWeakCache`](../-writable-weak-cache/index.md)`<`[`V`](../load-weak-cache-or-null.md#V)`>?`<br>`suspend fun <K, V> `[`Repository`](../-repository/index.md)`<`[`K`](../load-weak-cache-or-null.md#K)`, `[`V`](../load-weak-cache-or-null.md#V)`>.loadWeakCacheOrNull(key: `[`K`](../load-weak-cache-or-null.md#K)`): `[`WeakCache`](../-weak-cache/index.md)`<`[`V`](../load-weak-cache-or-null.md#V)`>?`<br>loads a [WeakCache](../-weak-cache/index.md) or returns null if failed |
