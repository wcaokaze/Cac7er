[Cac7er](../../../index.md) / [cac7er](../../index.md) / [Cac7er](../index.md) / [Builder](./index.md)

# Builder

`class Builder` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cac7er.kt#L72)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Builder(cac7erName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [delegatees](delegatees.md) | `val delegatees: `[`MutableSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)`<`[`Cac7er`](../index.md)`>`<br>Normally, a instance of Cac7er cannot manage repositories in another Cac7er. |
| [idealTotalFileSize](ideal-total-file-size.md) | `var idealTotalFileSize: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>The total size of cache files to run [gc](../gc.md). |

### Functions

| Name | Summary |
|---|---|
| [build](build.md) | `fun build(dir: `[`File`](http://docs.oracle.com/javase/6/docs/api/java/io/File.html)`): `[`Cac7er`](../index.md) |
| [createRepository](create-repository.md) | `fun <K, V> createRepository(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, serializer: `[`Serializer`](../../../cac7er.serializer/-serializer.md)`<`[`V`](create-repository.md#V)`>, deserializer: `[`Deserializer`](../../../cac7er.serializer/-deserializer.md)`<`[`V`](create-repository.md#V)`>): `[`WritableRepository`](../../-writable-repository/index.md)`<`[`K`](create-repository.md#K)`, `[`V`](create-repository.md#V)`>`<br>equivalent to`fun <K, V> createRepository(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fileNameSupplier: (`[`K`](create-repository.md#K)`) -> `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, serializer: `[`Serializer`](../../../cac7er.serializer/-serializer.md)`<`[`V`](create-repository.md#V)`>, deserializer: `[`Deserializer`](../../../cac7er.serializer/-deserializer.md)`<`[`V`](create-repository.md#V)`>): `[`WritableRepository`](../../-writable-repository/index.md)`<`[`K`](create-repository.md#K)`, `[`V`](create-repository.md#V)`>` |
