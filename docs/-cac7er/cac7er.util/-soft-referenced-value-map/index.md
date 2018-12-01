[Cac7er](../../index.md) / [cac7er.util](../index.md) / [SoftReferencedValueMap](./index.md)

# SoftReferencedValueMap

`class SoftReferencedValueMap<in K, V>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/util/SoftReferencedValueMap.kt#L11)

Map whose values are referenced as [SoftReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/SoftReference.html). This doesn't implement
[MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html) because it's peculiar.

**Since**
1.0.0

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SoftReferencedValueMap()`<br>Map whose values are referenced as [SoftReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/SoftReference.html). This doesn't implement [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html) because it's peculiar. |

### Functions

| Name | Summary |
|---|---|
| [get](get.md) | `operator fun get(key: `[`K`](index.md#K)`): `[`V`](index.md#V)`?` |
| [set](set.md) | `operator fun set(key: `[`K`](index.md#K)`, value: `[`V`](index.md#V)`): `[`V`](index.md#V)`?` |
