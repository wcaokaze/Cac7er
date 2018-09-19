package cac7er

import kotlinx.coroutines.experimental.*

internal class CacheImpl<T>(private val uniformizer: Uniformizer<T>)
      : WritableCache<T>
{
   override fun get(time: Long, accessCount: Float): T {
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

   override fun toLazyCache() = LazyCacheImpl(uniformizer)
   override fun toWeakCache() = WeakCacheImpl(uniformizer)
}
