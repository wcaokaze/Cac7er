package cac7er

import kotlinx.coroutines.experimental.*

internal class WeakCacheImpl<T>(private val uniformizer: Uniformizer<T>)
      : WritableWeakCache<T>
{
   override fun get(time: Long, accessCount: Float): T? {
      if (uniformizer.state == Uniformizer.State.DELETED) return null

      uniformizer.repository.launch(Dispatchers.IO) {
         uniformizer.circulationRecord.add(time, accessCount)
         saveCirculationRecord(uniformizer)
      }

      return uniformizer.content
   }

   override fun save(content: T) {
      uniformizer.content = content

      uniformizer.repository.launch(Dispatchers.IO) {
         save(uniformizer)
      }
   }

   override fun addObserver(observer: (T) -> Unit) {
      TODO()
   }

   override fun addObserver(owner: Any, observer: (T) -> Unit) {
      TODO()
   }

   override fun removeObserver(observer: (T) -> Unit) {
      TODO()
   }

   override fun toCache(): WritableCache<T>? {
      return if (uniformizer.state == Uniformizer.State.DELETED) {
         null
      } else {
         CacheImpl(uniformizer)
      }
   }

   override fun toLazyCache(): WritableLazyCache<T>? {
      return if (uniformizer.state == Uniformizer.State.DELETED) {
         null
      } else {
         LazyCacheImpl(uniformizer)
      }
   }
}
