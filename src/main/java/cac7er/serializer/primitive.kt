package cac7er.serializer

import java.io.*

fun Boolean.serialize(output: OutputStream) {
   output.write(if (this) 1 else 0)
}

fun deserializeBoolean(input: InputStream): Boolean {
   return when (input.read()) {
      0 -> false
      1 -> true
      else -> throw IOException()
   }
}

fun Byte.serialize(output: OutputStream) {
   output.write(this.toInt())
}

fun deserializeByte(input: InputStream): Byte {
   return input.read().toByte()
}

fun Char.serialize(output: OutputStream) {
   val b = byteArrayOf((this.toInt() ushr 8).toByte(), this.toByte())
   output.write(b)
}

fun deserializeChar(input: InputStream): Char {
   val b = ByteArray(2)
   input.read(b)

   val i = (b[0].toInt() shl 8) or (b[1].toInt())
   return i.toChar()
}

fun Short.serialize(output: OutputStream) {
   val b = byteArrayOf((this.toInt() ushr 8).toByte(), this.toByte())
   output.write(b)
}

fun deserializeShort(input: InputStream): Short {
   val b = ByteArray(2)
   input.read(b)

   val i = (b[0].toInt() shl 8) or (b[1].toInt())
   return i.toShort()
}

fun Int.serialize(output: OutputStream) {
   val b = byteArrayOf(
         (this ushr 24).toByte(),
         (this ushr 16).toByte(),
         (this ushr  8).toByte(),
          this         .toByte())

   output.write(b)
}

fun deserializeInt(input: InputStream): Int {
   val b = ByteArray(4)
   input.read(b)

   return (b[0].toInt() shl 24) or
          (b[1].toInt() shl 16) or
          (b[2].toInt() shl  8) or
           b[3].toInt()
}

fun Long.serialize(output: OutputStream) {
   val b = byteArrayOf(
         (this ushr 56).toByte(),
         (this ushr 48).toByte(),
         (this ushr 40).toByte(),
         (this ushr 32).toByte(),
         (this ushr 24).toByte(),
         (this ushr 16).toByte(),
         (this ushr  8).toByte(),
          this         .toByte())

   output.write(b)
}

fun deserializeLong(input: InputStream): Long {
   val b = ByteArray(8)
   input.read(b)

   return (b[0].toLong() shl 56) or
          (b[1].toLong() shl 48) or
          (b[2].toLong() shl 40) or
          (b[3].toLong() shl 32) or
          (b[4].toLong() shl 24) or
          (b[5].toLong() shl 16) or
          (b[6].toLong() shl  8) or
           b[7].toLong()
}

fun Float.serialize(output: OutputStream) {
   val i = toRawBits()
   i.serialize(output)
}

fun deserializeFloat(input: InputStream): Float {
   val i = deserializeInt(input)
   return Float.fromBits(i)
}

fun Double.serialize(output: OutputStream) {
   val l = toRawBits()
   l.serialize(output)
}

fun deserializeDouble(input: InputStream): Double {
   val l = deserializeLong(input)
   return Double.fromBits(l)
}
