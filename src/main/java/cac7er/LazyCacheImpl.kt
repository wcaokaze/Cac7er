package cac7er

import kotlinx.coroutines.experimental.*

internal class LazyCacheImpl<T>(private val uniformizer: Uniformizer<T>)
      : WritableLazyCache<T>
{
   override suspend fun get(time: Long, accessCount: Float): T {
      uniformizer.loadIfNecessary()

      uniformizer.repository.launch {
         uniformizer.circulationRecord.add(time, accessCount)
         saveCirculationRecord(uniformizer)
      }

      return uniformizer.content
   }

   override fun getIfAlreadyLoaded(time: Long, accessCount: Float): T? {
      if (uniformizer.state != Uniformizer.State.INITIALIZED) return null

      uniformizer.repository.launch {
         uniformizer.circulationRecord.add(time, accessCount)
         saveCirculationRecord(uniformizer)
      }

      return uniformizer.content
   }

   override fun save(content: T) {
      uniformizer.content = content

      uniformizer.repository.launch {
         save(uniformizer)
      }
   }

   override fun addObserver(observer: (T) -> Unit) {
      uniformizer.addObserver(observer)
   }

   override fun addObserver(owner: Any, observer: (T) -> Unit) {
      uniformizer.addObserver(owner, observer)
   }

   override fun removeObserver(observer: (T) -> Unit) {
      uniformizer.removeObserver(observer)
   }

   override suspend fun toCache(): WritableCache<T> {
      uniformizer.loadIfNecessary()
      return CacheImpl(uniformizer)
   }

   override suspend fun toWeakCache(): WritableWeakCache<T> {
      uniformizer.loadIfNecessary()
      return WeakCacheImpl(uniformizer)
   }
}
