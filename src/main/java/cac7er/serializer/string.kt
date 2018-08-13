package cac7er.serializer

import cac7er.*

fun CacheOutput.writeString(value: String) {
   val charArray = value.toCharArray()
   writeCharArray(charArray)
}

fun CacheInput.readString(): String {
   val charArray = readCharArray()
   return String(charArray)
}

fun CacheOutput.writeUtf8(value: String) {
   val byteArray = value.toByteArray(Charsets.UTF_8)
   writeByteArray(byteArray)
}

fun CacheInput.readUtf8(): String {
   val byteArray = readByteArray()
   return String(byteArray, Charsets.UTF_8)
}
