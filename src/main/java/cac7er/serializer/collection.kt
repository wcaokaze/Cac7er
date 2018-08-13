package cac7er.serializer

import cac7er.*

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

inline fun <K, V> CacheOutput.writeMap(value: Map<K, V>,
                                       keySerializer: Serializer<K>,
                                       valueSerializer: Serializer<V>)
{
   writeInt(value.size)

   for ((k, v) in value) {
      keySerializer(k)
      valueSerializer(v)
   }
}

inline fun <K, V> CacheInput.readMap(
      keyDeserializer: Deserializer<K>,
      valueDeserializer: Deserializer<V>): Map<K, V>
{
   return readMutableMap(keyDeserializer, valueDeserializer)
}

inline fun <K, V> CacheInput.readMutableMap(
      keyDeserializer: Deserializer<K>,
      valueDeserializer: Deserializer<V>): MutableMap<K, V>
{
   val size = readInt()
   val map = HashMap<K, V>()

   for (i in 0 until size) {
      val key = keyDeserializer()
      val value = valueDeserializer()

      map[key] = value
   }

   return map
}

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
