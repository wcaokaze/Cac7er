[Cac7er](../../../index.md) / [cac7er](../../index.md) / [Cac7er](../index.md) / [Builder](index.md) / [createRepository](./create-repository.md)

# createRepository

`fun <K, V> createRepository(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, serializer: `[`Serializer`](../../../cac7er.serializer/-serializer.md)`<`[`V`](create-repository.md#V)`>, deserializer: `[`Deserializer`](../../../cac7er.serializer/-deserializer.md)`<`[`V`](create-repository.md#V)`>): `[`WritableRepository`](../../-writable-repository/index.md)`<`[`K`](create-repository.md#K)`, `[`V`](create-repository.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cac7er.kt#L84)

equivalent to

```
createRepository(name, { it.toString() },
      serializer, deserializer)
```

**Since**
0.1.0

`fun <K, V> createRepository(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fileNameSupplier: (`[`K`](create-repository.md#K)`) -> `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, serializer: `[`Serializer`](../../../cac7er.serializer/-serializer.md)`<`[`V`](create-repository.md#V)`>, deserializer: `[`Deserializer`](../../../cac7er.serializer/-deserializer.md)`<`[`V`](create-repository.md#V)`>): `[`WritableRepository`](../../-writable-repository/index.md)`<`[`K`](create-repository.md#K)`, `[`V`](create-repository.md#V)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cac7er.kt#L92)