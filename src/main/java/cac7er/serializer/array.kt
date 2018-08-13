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
