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
      : WritableRepository<K, V>, CoroutineScope by cac7er
{
   private val uniformizerPool = Pool<K, Uniformizer<V>> { key ->
      Uniformizer(this, fileNameSupplier(key))
   }

   val dir = File(cac7er.dir, name).apply {
      if (!exists()) {
         if (!mkdir()) throw IOException("cannot mkdir")
      }
   }

   override fun save(key: K, value: V): WritableCache<V> {
      val uniformizer = uniformizerPool[key]

      uniformizer.content = value

      launch {
         save(uniformizer)
      }

      return CacheImpl(uniformizer)
   }

   override suspend fun load(key: K): WritableCache<V> {
      val uniformizer = uniformizerPool[key]

      uniformizer.loadIfNecessary()
      if (uniformizer.state == Uniformizer.State.DELETED) throw IOException()

      return CacheImpl(uniformizer)
   }

   override suspend fun loadWeakCache(key: K): WritableWeakCache<V> {
      val uniformizer = uniformizerPool[key]

      uniformizer.loadIfNecessary()

      return WeakCacheImpl(uniformizer)
   }

   override fun loadLazyCache(key: K): WritableLazyCache<V> {
      val uniformizer = uniformizerPool[key]
      return LazyCacheImpl(uniformizer)
   }

   private suspend fun loadUniformizer(key: K): Uniformizer<V> {
      val uniformizer = uniformizerPool[key]
      uniformizer.loadIfNecessary()
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

internal suspend fun Uniformizer<*>.loadIfNecessary() {
   if (state != Uniformizer.State.EMPTY) return

   onStartToLoadContent()
   repository.async { load(this@loadIfNecessary) } .await()
}
