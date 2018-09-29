package cac7er

import kotlinx.coroutines.experimental.*

internal class WeakCacheImpl<T>(private val uniformizer: Uniformizer<T>)
      : WritableWeakCache<T>
{
   val repository: RepositoryImpl<*, T> get() = uniformizer.repository
   val cac7er: Cac7er                   get() = uniformizer.repository.cac7er
   val fileName: String                 get() = uniformizer.fileName

   override fun get(time: Long, accessCount: Float): T? {
      if (uniformizer.state == Uniformizer.State.DELETED) return null

      if (accessCount != .0f) {
         uniformizer.repository.launch {
            uniformizer.circulationRecord.add(time, accessCount)
            saveCirculationRecord(uniformizer)
         }
      }

      return uniformizer.weakContent
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
