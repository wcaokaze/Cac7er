package cac7er.serializer

import cac7er.*

private const val BUFFER_SIZE = 256

fun CacheOutput.writeBooleanArray(value: BooleanArray) {
   writeInt(value.size)

   for (b in value) {
      writeBoolean(b)
   }
}

fun CacheInput.readBooleanArray(): BooleanArray {
   val size = readInt()

   return BooleanArray(size) { readBoolean() }
}

fun CacheOutput.writeByteArray(value: ByteArray) {
   writeInt(value.size)

   for (b in value) {
      writeByte(b)
   }
}

fun CacheInput.readByteArray(): ByteArray {
   val size = readInt()

   return ByteArray(size) { readByte() }
}

fun CacheOutput.writeCharArray(value: CharArray) {
   writeInt(value.size)

   for (c in value) {
      writeChar(c)
   }
}

fun CacheInput.readCharArray(): CharArray {
   val size = readInt()

   return CharArray(size) { readChar() }
}

fun CacheOutput.writeShortArray(value: ShortArray) {
   writeInt(value.size)

   for (s in value) {
      writeShort(s)
   }
}

fun CacheInput.readShortArray(): ShortArray {
   val size = readInt()

   return ShortArray(size) { readShort() }
}

fun CacheOutput.writeIntArray(value: IntArray) {
   writeInt(value.size)

   for (i in value) {
      writeInt(i)
   }
}

fun CacheInput.readIntArray(): IntArray {
   val size = readInt()

   return IntArray(size) { readInt() }
}

fun CacheOutput.writeLongArray(value: LongArray) {
   writeInt(value.size)

   for (l in value) {
      writeLong(l)
   }
}

fun CacheInput.readLongArray(): LongArray {
   val size = readInt()

   return LongArray(size) { readLong() }
}

fun CacheOutput.writeFloatArray(value: FloatArray) {
   writeInt(value.size)

   for (f in value) {
      writeFloat(f)
   }
}

fun CacheInput.readFloatArray(): FloatArray {
   val size = readInt()

   return FloatArray(size) { readFloat() }
}

fun CacheOutput.writeDoubleArray(value: DoubleArray) {
   writeInt(value.size)

   for (d in value) {
      writeDouble(d)
   }
}

fun CacheInput.readDoubleArray(): DoubleArray {
   val size = readInt()

   return DoubleArray(size) { readDouble() }
}
