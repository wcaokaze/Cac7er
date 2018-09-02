package cac7er.serializer.collection

import cac7er.serializer.*

inline fun <T> CacheOutput.writeSet
      (value: Set<T>, elementSerializer: Serializer<T>)
{
   writeInt(value.size)

   for (t in value) {
      elementSerializer(t)
   }
}

inline fun <T> CacheInput.readSet(elementDeserializer: Deserializer<T>): Set<T> {
   return readMutableSet(elementDeserializer)
}

inline fun <T> CacheInput.readMutableSet
      (elementDeserializer: Deserializer<T>): MutableSet<T>
{
   val size = readInt()
   val set = HashSet<T>()

   for (i in 0 until size) {
      set += elementDeserializer()
   }

   return set
}
