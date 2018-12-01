[Cac7er](../../index.md) / [cac7er.util](../index.md) / [Pool](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`Pool(valueSupplier: (`[`K`](index.md#K)`) -> `[`V`](index.md#V)`)`

looks like a [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html) whose values are created automatically, and removed
automatically.

If this Pool doesn't have the value, [get](get.md) will invoke valueSupplier. The
values are referenced as [SoftReference](http://docs.oracle.com/javase/6/docs/api/java/lang/ref/SoftReference.html). GC may collect them.

**Since**
0.1.0

