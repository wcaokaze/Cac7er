[Cac7er](../index.md) / [cac7er.serializer](./index.md)

## Package cac7er.serializer

### Types

| Name | Summary |
|---|---|
| [CacheInput](-cache-input.md) | `class CacheInput` |
| [CacheOutput](-cache-output.md) | `class CacheOutput` |

### Type Aliases

| Name | Summary |
|---|---|
| [Deserializer](-deserializer.md) | `typealias Deserializer<T> = `[`CacheInput`](-cache-input.md)`.() -> `[`T`](-deserializer.md#T) |
| [Serializer](-serializer.md) | `typealias Serializer<T> = `[`CacheOutput`](-cache-output.md)`.(value: `[`T`](-serializer.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Functions

| Name | Summary |
|---|---|
| [readArray](read-array.md) | `fun <T> `[`CacheInput`](-cache-input.md)`.readArray(elementDeserializer: `[`Deserializer`](-deserializer.md)`<`[`T`](read-array.md#T)`>): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`T`](read-array.md#T)`>` |
| [readBoolean](read-boolean.md) | `fun `[`CacheInput`](-cache-input.md)`.readBoolean(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [readBooleanArray](read-boolean-array.md) | `fun `[`CacheInput`](-cache-input.md)`.readBooleanArray(): `[`BooleanArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean-array/index.html) |
| [readByte](read-byte.md) | `fun `[`CacheInput`](-cache-input.md)`.readByte(): `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html) |
| [readByteArray](read-byte-array.md) | `fun `[`CacheInput`](-cache-input.md)`.readByteArray(): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [readCache](read-cache.md) | `fun <T> `[`CacheInput`](-cache-input.md)`.readCache(): `[`Cache`](../cac7er/-cache/index.md)`<`[`T`](read-cache.md#T)`>` |
| [readChar](read-char.md) | `fun `[`CacheInput`](-cache-input.md)`.readChar(): `[`Char`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char/index.html) |
| [readCharArray](read-char-array.md) | `fun `[`CacheInput`](-cache-input.md)`.readCharArray(): `[`CharArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-array/index.html) |
| [readDouble](read-double.md) | `fun `[`CacheInput`](-cache-input.md)`.readDouble(): `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [readDoubleArray](read-double-array.md) | `fun `[`CacheInput`](-cache-input.md)`.readDoubleArray(): `[`DoubleArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html) |
| [readFloat](read-float.md) | `fun `[`CacheInput`](-cache-input.md)`.readFloat(): `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [readFloatArray](read-float-array.md) | `fun `[`CacheInput`](-cache-input.md)`.readFloatArray(): `[`FloatArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float-array/index.html) |
| [readInt](read-int.md) | `fun `[`CacheInput`](-cache-input.md)`.readInt(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [readIntArray](read-int-array.md) | `fun `[`CacheInput`](-cache-input.md)`.readIntArray(): `[`IntArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html) |
| [readLazyCache](read-lazy-cache.md) | `fun <T> `[`CacheInput`](-cache-input.md)`.readLazyCache(): `[`LazyCache`](../cac7er/-lazy-cache/index.md)`<`[`T`](read-lazy-cache.md#T)`>` |
| [readLong](read-long.md) | `fun `[`CacheInput`](-cache-input.md)`.readLong(): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [readLongArray](read-long-array.md) | `fun `[`CacheInput`](-cache-input.md)`.readLongArray(): `[`LongArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/index.html) |
| [readNullable](read-nullable.md) | `fun <T> `[`CacheInput`](-cache-input.md)`.readNullable(deserializer: `[`Deserializer`](-deserializer.md)`<`[`T`](read-nullable.md#T)`>): `[`T`](read-nullable.md#T)`?` |
| [readShort](read-short.md) | `fun `[`CacheInput`](-cache-input.md)`.readShort(): `[`Short`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html) |
| [readShortArray](read-short-array.md) | `fun `[`CacheInput`](-cache-input.md)`.readShortArray(): `[`ShortArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short-array/index.html) |
| [readString](read-string.md) | `fun `[`CacheInput`](-cache-input.md)`.readString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [readUtf8](read-utf8.md) | `fun `[`CacheInput`](-cache-input.md)`.readUtf8(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [readWeakCache](read-weak-cache.md) | `fun <T> `[`CacheInput`](-cache-input.md)`.readWeakCache(): `[`WeakCache`](../cac7er/-weak-cache/index.md)`<`[`T`](read-weak-cache.md#T)`>` |
| [writeArray](write-array.md) | `fun <T> `[`CacheOutput`](-cache-output.md)`.writeArray(value: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<out `[`T`](write-array.md#T)`>, elementSerializer: `[`Serializer`](-serializer.md)`<`[`T`](write-array.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeBoolean](write-boolean.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeBoolean(value: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeBooleanArray](write-boolean-array.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeBooleanArray(value: `[`BooleanArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeByte](write-byte.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeByte(value: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeByteArray](write-byte-array.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeByteArray(value: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeCache](write-cache.md) | `fun <T> `[`CacheOutput`](-cache-output.md)`.writeCache(value: `[`Cache`](../cac7er/-cache/index.md)`<`[`T`](write-cache.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeChar](write-char.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeChar(value: `[`Char`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeCharArray](write-char-array.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeCharArray(value: `[`CharArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeDouble](write-double.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeDouble(value: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeDoubleArray](write-double-array.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeDoubleArray(value: `[`DoubleArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeFloat](write-float.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeFloat(value: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeFloatArray](write-float-array.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeFloatArray(value: `[`FloatArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeInt](write-int.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeInt(value: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeIntArray](write-int-array.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeIntArray(value: `[`IntArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeLazyCache](write-lazy-cache.md) | `fun <T> `[`CacheOutput`](-cache-output.md)`.writeLazyCache(value: `[`LazyCache`](../cac7er/-lazy-cache/index.md)`<`[`T`](write-lazy-cache.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeLong](write-long.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeLong(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeLongArray](write-long-array.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeLongArray(value: `[`LongArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeNullable](write-nullable.md) | `fun <T> `[`CacheOutput`](-cache-output.md)`.writeNullable(value: `[`T`](write-nullable.md#T)`?, serializer: `[`Serializer`](-serializer.md)`<`[`T`](write-nullable.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeShort](write-short.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeShort(value: `[`Short`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeShortArray](write-short-array.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeShortArray(value: `[`ShortArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short-array/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeString](write-string.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeString(value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeUtf8](write-utf8.md) | `fun `[`CacheOutput`](-cache-output.md)`.writeUtf8(value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeWeakCache](write-weak-cache.md) | `fun <T> `[`CacheOutput`](-cache-output.md)`.writeWeakCache(value: `[`WeakCache`](../cac7er/-weak-cache/index.md)`<`[`T`](write-weak-cache.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
