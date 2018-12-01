[Cac7er](../index.md) / [cac7er.serializer](index.md) / [CacheInput](./-cache-input.md)

# CacheInput

`class CacheInput` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/serializer/CacheIO.kt#L19)

### Extension Functions

| Name | Summary |
|---|---|
| [checkCacheVersionUID](../cac7er.serializer.util/check-cache-version-u-i-d.md) | `fun `[`CacheInput`](./-cache-input.md)`.checkCacheVersionUID(expected: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [readArray](read-array.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readArray(elementDeserializer: `[`Deserializer`](-deserializer.md)`<`[`T`](read-array.md#T)`>): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`T`](read-array.md#T)`>` |
| [readArrayList](../cac7er.serializer.collection/read-array-list.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readArrayList(elementDeserializer: `[`Deserializer`](-deserializer.md)`<`[`T`](../cac7er.serializer.collection/read-array-list.md#T)`>): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`T`](../cac7er.serializer.collection/read-array-list.md#T)`>` |
| [readBoolean](read-boolean.md) | `fun `[`CacheInput`](./-cache-input.md)`.readBoolean(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [readBooleanArray](read-boolean-array.md) | `fun `[`CacheInput`](./-cache-input.md)`.readBooleanArray(): `[`BooleanArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean-array/index.html) |
| [readByte](read-byte.md) | `fun `[`CacheInput`](./-cache-input.md)`.readByte(): `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html) |
| [readByteArray](read-byte-array.md) | `fun `[`CacheInput`](./-cache-input.md)`.readByteArray(): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [readCache](read-cache.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readCache(): `[`Cache`](../cac7er/-cache/index.md)`<`[`T`](read-cache.md#T)`>` |
| [readChar](read-char.md) | `fun `[`CacheInput`](./-cache-input.md)`.readChar(): `[`Char`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char/index.html) |
| [readCharArray](read-char-array.md) | `fun `[`CacheInput`](./-cache-input.md)`.readCharArray(): `[`CharArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-array/index.html) |
| [readDate](../cac7er.serializer.date/read-date.md) | `fun `[`CacheInput`](./-cache-input.md)`.readDate(): `[`Date`](http://docs.oracle.com/javase/6/docs/api/java/util/Date.html) |
| [readDateWithTimeZone](../cac7er.serializer.date/read-date-with-time-zone.md) | `fun `[`CacheInput`](./-cache-input.md)`.readDateWithTimeZone(timeZone: `[`TimeZone`](http://docs.oracle.com/javase/6/docs/api/java/util/TimeZone.html)` = TimeZone.getDefault()): `[`Date`](http://docs.oracle.com/javase/6/docs/api/java/util/Date.html) |
| [readDouble](read-double.md) | `fun `[`CacheInput`](./-cache-input.md)`.readDouble(): `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [readDoubleArray](read-double-array.md) | `fun `[`CacheInput`](./-cache-input.md)`.readDoubleArray(): `[`DoubleArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html) |
| [readFloat](read-float.md) | `fun `[`CacheInput`](./-cache-input.md)`.readFloat(): `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [readFloatArray](read-float-array.md) | `fun `[`CacheInput`](./-cache-input.md)`.readFloatArray(): `[`FloatArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float-array/index.html) |
| [readHashMap](../cac7er.serializer.collection/read-hash-map.md) | `fun <K, V> `[`CacheInput`](./-cache-input.md)`.readHashMap(keyDeserializer: `[`Deserializer`](-deserializer.md)`<`[`K`](../cac7er.serializer.collection/read-hash-map.md#K)`>, valueDeserializer: `[`Deserializer`](-deserializer.md)`<`[`V`](../cac7er.serializer.collection/read-hash-map.md#V)`>): `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`K`](../cac7er.serializer.collection/read-hash-map.md#K)`, `[`V`](../cac7er.serializer.collection/read-hash-map.md#V)`>` |
| [readHashSet](../cac7er.serializer.collection/read-hash-set.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readHashSet(elementDeserializer: `[`Deserializer`](-deserializer.md)`<`[`T`](../cac7er.serializer.collection/read-hash-set.md#T)`>): `[`MutableSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)`<`[`T`](../cac7er.serializer.collection/read-hash-set.md#T)`>` |
| [readInt](read-int.md) | `fun `[`CacheInput`](./-cache-input.md)`.readInt(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [readIntArray](read-int-array.md) | `fun `[`CacheInput`](./-cache-input.md)`.readIntArray(): `[`IntArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html) |
| [readLazyCache](read-lazy-cache.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readLazyCache(): `[`LazyCache`](../cac7er/-lazy-cache/index.md)`<`[`T`](read-lazy-cache.md#T)`>` |
| [readList](../cac7er.serializer.collection/read-list.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readList(elementDeserializer: `[`Deserializer`](-deserializer.md)`<`[`T`](../cac7er.serializer.collection/read-list.md#T)`>): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`T`](../cac7er.serializer.collection/read-list.md#T)`>` |
| [readLong](read-long.md) | `fun `[`CacheInput`](./-cache-input.md)`.readLong(): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [readLongArray](read-long-array.md) | `fun `[`CacheInput`](./-cache-input.md)`.readLongArray(): `[`LongArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/index.html) |
| [readMap](../cac7er.serializer.collection/read-map.md) | `fun <K, V> `[`CacheInput`](./-cache-input.md)`.readMap(keyDeserializer: `[`Deserializer`](-deserializer.md)`<`[`K`](../cac7er.serializer.collection/read-map.md#K)`>, valueDeserializer: `[`Deserializer`](-deserializer.md)`<`[`V`](../cac7er.serializer.collection/read-map.md#V)`>): `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`K`](../cac7er.serializer.collection/read-map.md#K)`, `[`V`](../cac7er.serializer.collection/read-map.md#V)`>` |
| [readNullable](read-nullable.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readNullable(deserializer: `[`Deserializer`](-deserializer.md)`<`[`T`](read-nullable.md#T)`>): `[`T`](read-nullable.md#T)`?` |
| [readSet](../cac7er.serializer.collection/read-set.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readSet(elementDeserializer: `[`Deserializer`](-deserializer.md)`<`[`T`](../cac7er.serializer.collection/read-set.md#T)`>): `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`T`](../cac7er.serializer.collection/read-set.md#T)`>` |
| [readShort](read-short.md) | `fun `[`CacheInput`](./-cache-input.md)`.readShort(): `[`Short`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html) |
| [readShortArray](read-short-array.md) | `fun `[`CacheInput`](./-cache-input.md)`.readShortArray(): `[`ShortArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short-array/index.html) |
| [readString](read-string.md) | `fun `[`CacheInput`](./-cache-input.md)`.readString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [readUrl](../cac7er.serializer.net/read-url.md) | `fun `[`CacheInput`](./-cache-input.md)`.readUrl(): `[`URL`](http://docs.oracle.com/javase/6/docs/api/java/net/URL.html) |
| [readUtf8](read-utf8.md) | `fun `[`CacheInput`](./-cache-input.md)`.readUtf8(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [readWeakCache](read-weak-cache.md) | `fun <T> `[`CacheInput`](./-cache-input.md)`.readWeakCache(): `[`WeakCache`](../cac7er/-weak-cache/index.md)`<`[`T`](read-weak-cache.md#T)`>` |
