package cac7er.serializer

import java.io.*

inline fun <T> CacheOutput.writeNullable(value: T?, serializer: Serializer<T>) {
   if (value == null) {
      writeByte(0)
   } else {
      writeByte(1)
      serializer(value)
   }
}

inline fun <T> CacheInput.readNullable(deserializer: Deserializer<T>): T? {
   return when (readByte().toInt()) {
      0 -> null
      1 -> deserializer()
      else -> throw IOException()
   }
}

fun CacheOutput.writeBoolean(value: Boolean) {
   val b = if (value) 1 else 0
   stream.write(b)
}

fun CacheInput.readBoolean(): Boolean {
   return when (stream.read()) {
      0 -> false
      1 -> true
      else -> throw IOException()
   }
}

fun CacheOutput.writeByte(value: Byte) {
   stream.write(value.toInt())
}

fun CacheInput.readByte(): Byte {
   return stream.read().toByte()
}

fun CacheOutput.writeChar(value: Char) {
   val b = byteArrayOf((value.toInt() ushr 8).toByte(), value.toByte())
   stream.write(b)
}

fun CacheInput.readChar(): Char {
   val b = ByteArray(2)
   stream.read(b)

   val i = (b[0].toInt() shl 8) or (b[1].toInt())
   return i.toChar()
}

fun CacheOutput.writeShort(value: Short) {
   val b = byteArrayOf((value.toInt() ushr 8).toByte(), value.toByte())
   stream.write(b)
}

fun CacheInput.readShort(): Short {
   val b = ByteArray(2)
   stream.read(b)

   val i = (b[0].toInt() shl 8) or (b[1].toInt())
   return i.toShort()
}

fun CacheOutput.writeInt(value: Int) {
   val b = byteArrayOf(
         (value ushr 24).toByte(),
         (value ushr 16).toByte(),
         (value ushr  8).toByte(),
          value         .toByte())

   stream.write(b)
}

fun CacheInput.readInt(): Int {
   val b = ByteArray(4)
   stream.read(b)

   return (b[0].toInt() shl 24) or
          (b[1].toInt() shl 16) or
          (b[2].toInt() shl  8) or
           b[3].toInt()
}

fun CacheOutput.writeLong(value: Long) {
   val b = byteArrayOf(
         (value ushr 56).toByte(),
         (value ushr 48).toByte(),
         (value ushr 40).toByte(),
         (value ushr 32).toByte(),
         (value ushr 24).toByte(),
         (value ushr 16).toByte(),
         (value ushr  8).toByte(),
          value         .toByte())

   stream.write(b)
}

fun CacheInput.readLong(): Long {
   val b = ByteArray(8)
   stream.read(b)

   return (b[0].toLong() shl 56) or
          (b[1].toLong() shl 48) or
          (b[2].toLong() shl 40) or
          (b[3].toLong() shl 32) or
          (b[4].toLong() shl 24) or
          (b[5].toLong() shl 16) or
          (b[6].toLong() shl  8) or
           b[7].toLong()
}

fun CacheOutput.writeFloat(value: Float) {
   val i = value.toRawBits()
   writeInt(i)
}

fun CacheInput.readFloat(): Float {
   val i = readInt()
   return Float.fromBits(i)
}

fun CacheOutput.writeDouble(value: Double) {
   val l = value.toRawBits()
   writeLong(l)
}

fun CacheInput.readDouble(): Double {
   val l = readLong()
   return Double.fromBits(l)
}
