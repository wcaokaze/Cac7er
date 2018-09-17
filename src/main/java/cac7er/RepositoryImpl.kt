package cac7er

import java.io.*
import cac7er.serializer.*

import kotlinx.coroutines.experimental.*

import cac7er.util.Pool

internal class RepositoryImpl<in K, V>
      constructor(
            val cac7er: Cac7er,
            override val name: String,
            private val fileNameSupplier: (K) -> String,
            val serializer: Serializer<V>,
            val deserializer: Deserializer<V>
      )
      : WritableRepository<K, V>, CoroutineScope
{
   private val uniformizerPool = Pool<K, Uniformizer<V>> { key ->
      Uniformizer(this, fileNameSupplier(key))
   }

   private val job = Job()
   override val coroutineContext get() = job + Dispatchers.IO

   val dir = File(cac7er.dir, name).apply {
      if (!exists()) {
         if (!mkdir()) throw IOException("cannot mkdir")
      }
   }

   override fun save(key: K, value: V): WritableCache<V> {
      val uniformizer = uniformizerPool[key]
      uniformizer.content = value
      save(uniformizer)

      TODO()
   }

   override suspend fun load(key: K): WritableCache<V> {
      TODO()
   }

   override suspend fun loadWeakCache(key: K): WritableWeakCache<V>? {
      TODO()
   }

   override fun loadLazyCache(key: K): WritableLazyCache<V> {
      TODO()
   }

   private suspend fun loadUniformizer(key: K): Uniformizer<V> {
      val uniformizer = uniformizerPool[key]
      uniformizer.onStartToLoadContent()

      async { load(uniformizer) } .await()

      return uniformizer
   }

   override fun addObserver(key: K, observer: (V) -> Unit) {
      TODO()
   }

   override fun addObserver(owner: Any, key: K, observer: (V) -> Unit) {
      TODO()
   }

   override fun removeObserver(key: K, observer: (V) -> Unit) {
      TODO()
   }
}
