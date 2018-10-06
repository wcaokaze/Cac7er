package cac7er.serializer

inline fun <T> CacheOutput.writeNullable(value: T?, serializer: Serializer<T>) {
   if (value == null) {
      writeBoolean(true)
   } else {
      writeBoolean(false)
      serializer(value)
   }
}

inline fun <T> CacheInput.readNullable(deserializer: Deserializer<T>): T? {
   return if (readBoolean()) {
      null
   } else {
      deserializer()
   }
}

fun CacheOutput.writeBoolean(value: Boolean) {
   output.writeBoolean(value)
}

fun CacheInput.readBoolean(): Boolean = input.readBoolean()

fun CacheOutput.writeByte(value: Byte) {
   output.writeByte(value.toInt())
}

fun CacheInput.readByte(): Byte = input.readByte()

fun CacheOutput.writeChar(value: Char) {
   output.writeChar(value.toInt())
}

fun CacheInput.readChar(): Char = input.readChar()

fun CacheOutput.writeShort(value: Short) {
   output.writeShort(value.toInt())
}

fun CacheInput.readShort(): Short = input.readShort()

fun CacheOutput.writeInt(value: Int) {
   output.writeInt(value)
}

fun CacheInput.readInt(): Int = input.readInt()

fun CacheOutput.writeLong(value: Long) {
   output.writeLong(value)
}

fun CacheInput.readLong(): Long = input.readLong()

fun CacheOutput.writeFloat(value: Float) {
   output.writeFloat(value)
}

fun CacheInput.readFloat(): Float = input.readFloat()

fun CacheOutput.writeDouble(value: Double) {
   output.writeDouble(value)
}

fun CacheInput.readDouble(): Double = input.readDouble()
