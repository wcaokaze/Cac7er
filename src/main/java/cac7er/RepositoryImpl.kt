package cac7er

import java.io.*
import cac7er.serializer.*

import kotlinx.coroutines.*

import cac7er.util.Pool

internal class RepositoryImpl<in K, V>
      constructor(
            override val name: String,
            private val fileNameSupplier: (K) -> String,
            val serializer: Serializer<V>,
            val deserializer: Deserializer<V>
      )
      : WritableRepository<K, V>, CoroutineScope
{
   private val uniformizerPool = Pool<String, Uniformizer<V>> { fileName ->
      Uniformizer(this, fileName)
   }

   lateinit var coroutineScope: CoroutineScope
      private set

   lateinit var dir: File
      private set

   override val coroutineContext get() = coroutineScope.coroutineContext

   private lateinit var _cac7er: Cac7er

   var cac7er: Cac7er
      get() = _cac7er
      set(value) {
         _cac7er = value

         coroutineScope = value

         dir = File(value.dir, name).apply {
            if (!exists()) {
               if (!mkdir()) throw IOException("cannot mkdir")
            }
         }
      }

   override fun save(key: K, value: V): WritableCache<V> {
      val fileName = fileNameSupplier(key)
      val uniformizer = uniformizerPool[fileName]

      uniformizer.content = value

      saveLazy(uniformizer)

      return CacheImpl(uniformizer)
   }

   override fun getFuture(key: K): FutureCache<V> {
      val fileName = fileNameSupplier(key)
      val uniformizer = uniformizerPool[fileName]

      return LazyCacheImpl(uniformizer)
   }

   override suspend fun load(key: K): WritableCache<V> {
      val fileName = fileNameSupplier(key)
      return load(fileName)
   }

   suspend fun load(fileName: String): CacheImpl<V> {
      val uniformizer = uniformizerPool[fileName]

      uniformizer.loadIfNecessary()
      if (uniformizer.state == Uniformizer.State.DELETED) throw IOException()

      return CacheImpl(uniformizer)
   }

   fun loadBlocking(fileName: String): CacheImpl<V> {
      val uniformizer = uniformizerPool[fileName]

      uniformizer.loadBlockingIfNecessary()
      if (uniformizer.state == Uniformizer.State.DELETED) throw IOException()

      return CacheImpl(uniformizer)
   }

   override suspend fun loadWeakCache(key: K): WritableWeakCache<V> {
      val fileName = fileNameSupplier(key)
      val uniformizer = uniformizerPool[fileName]

      uniformizer.loadIfNecessary()

      return WeakCacheImpl(uniformizer)
   }

   fun loadWeakCacheBlocking(fileName: String): WeakCacheImpl<V> {
      val uniformizer = uniformizerPool[fileName]

      uniformizer.loadBlockingIfNecessary()

      return WeakCacheImpl(uniformizer)
   }

   override fun loadLazyCache(key: K): WritableLazyCache<V> {
      val fileName = fileNameSupplier(key)
      val uniformizer = uniformizerPool[fileName]

      return LazyCacheImpl(uniformizer)
   }

   fun loadLazyCacheBlocking(fileName: String): LazyCacheImpl<V> {
      val uniformizer = uniformizerPool[fileName]

      return LazyCacheImpl(uniformizer)
   }

   override fun addObserver(key: K, observer: (Cache<V>, V) -> Unit) {
      val fileName = fileNameSupplier(key)
      uniformizerPool[fileName].addObserver(observer)
   }

   override fun removeObserver(key: K, observer: (Cache<V>, V) -> Unit) {
      val fileName = fileNameSupplier(key)
      uniformizerPool[fileName].removeObserver(observer)
   }
}
