package cac7er.serializer.collection

import cac7er.serializer.*

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
