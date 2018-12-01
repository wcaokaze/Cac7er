[Cac7er](../../index.md) / [cac7er.util](../index.md) / [Pool](./index.md)

# Pool

`class Pool<in K, out V>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/util/Pool.kt#L14)

looks like a [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html) whose values are created automatically, and removed
automatically.

If this Pool doesn't have the value, [get](get.md) will invoke valueSupplier. The
values are referenced as [SoftReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/SoftReference.html). GC may collect them.

**Since**
0.1.0

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Pool(valueSupplier: (`[`K`](index.md#K)`) -> `[`V`](index.md#V)`)`<br>looks like a [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html) whose values are created automatically, and removed automatically. |

### Functions

| Name | Summary |
|---|---|
| [get](get.md) | `operator fun get(key: `[`K`](index.md#K)`): `[`V`](index.md#V) |
