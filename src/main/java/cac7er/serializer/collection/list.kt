package cac7er.serializer.collection

import cac7er.serializer.*

inline fun <T> CacheOutput.writeList(value: List<T>,
                                                       elementSerializer: Serializer<T>)
{
   writeInt(value.size)

   for (t in value) {
      elementSerializer(t)
   }
}

inline fun <T> CacheInput.readList
      (elementDeserializer: Deserializer<T>): List<T>
{
   val size = readInt()

   return List(size) { elementDeserializer() }
}

inline fun <T> CacheInput.readMutableList
      (elementDeserializer: Deserializer<T>): MutableList<T>
{
   val size = readInt()

   return MutableList(size) { elementDeserializer() }
}
