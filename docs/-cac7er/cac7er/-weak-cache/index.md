[Cac7er](../../index.md) / [cac7er](../index.md) / [WeakCache](./index.md)

# WeakCache

`interface WeakCache<out T>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/WeakCache.kt#L26)

Cache which doesn't depend on the content. See [Cac7er.gc](../-cac7er/gc.md) first.

``` kotlin
class A
class B(val aCache: WeakCache<A>)
//                  ^^^^

val aCache = aRepository.save("a", A())
val bCache = bRepository.save("b", B(aCache))

// ...

Cac7er.gc(100L)
```

Then, [gc](../-cac7er/gc.md) doesn't mind relationship, does delete `a` freely.

**Since**
0.1.0

### Functions

| Name | Summary |
|---|---|
| [addObserver](add-observer.md) | `abstract fun addObserver(observer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>adds a function to observe this cache. Note that observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). Simplex lambda will be collected by GC. To avoid GC, the observer instance should be owned by any other instance. The easiest way is using [addObserver(Any, (T) -&gt; Unit)](add-observer.md).`abstract fun addObserver(owner: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, observer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>As mentioned in another overload, observers are referenced as [WeakReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/WeakReference.html). In this function the observer is associated with the specified owner instance, and can observe until the owner is GCed. |
| [get](get.md) | `abstract fun get(time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](index.md#T)`?`<br>`open fun get(accessCount: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)` = .0f): `[`T`](index.md#T)`?` |
| [removeObserver](remove-observer.md) | `abstract fun removeObserver(observer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>removes the observer. The name says it all. |
| [toCache](to-cache.md) | `abstract fun toCache(): `[`Cache`](../-cache/index.md)`<`[`T`](index.md#T)`>?`<br>converts this WeakCache to [Cache](../-cache/index.md). |
| [toLazyCache](to-lazy-cache.md) | `abstract fun toLazyCache(): `[`LazyCache`](../-lazy-cache/index.md)`<`[`T`](index.md#T)`>?`<br>converts this WeakCache to [LazyCache](../-lazy-cache/index.md). |

### Inheritors

| Name | Summary |
|---|---|
| [WritableWeakCache](../-writable-weak-cache/index.md) | `interface WritableWeakCache<T> : `[`WeakCache`](./index.md)`<`[`T`](../-writable-weak-cache/index.md#T)`>`<br>Writable WeakCache. The name says it all. |
