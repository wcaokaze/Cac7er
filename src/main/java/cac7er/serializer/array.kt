package cac7er.serializer

import cac7er.*

private const val BUFFER_SIZE = 256

fun CacheOutput.writeBooleanArray(value: BooleanArray) {
   val size = value.size

   val b = ByteArray(BUFFER_SIZE)

   writeInt(size)

   for (offset in 0 until size step BUFFER_SIZE) {
      if (offset + BUFFER_SIZE < size) {
         for (i in 0 until BUFFER_SIZE) {
            b[i] = if (value[offset + i]) 1 else 0
         }

         stream.write(b)
      } else {
         for (i in offset until size) {
            b[i] = if (value[i]) 1 else 0
         }

         stream.write(b, 0, size - offset)
      }
   }
}
