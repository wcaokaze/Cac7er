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
   return readArrayList(elementDeserializer)
}

inline fun <T> CacheInput.readArrayList
      (elementDeserializer: Deserializer<T>): MutableList<T>
{
   val size = readInt()
   val list = ArrayList<T>(size)

   for (i in 0 until size) {
      list += elementDeserializer()
   }

   return list
}
