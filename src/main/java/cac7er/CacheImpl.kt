package cac7er

import kotlinx.coroutines.*

internal class CacheImpl<T>(private val uniformizer: Uniformizer<T>)
      : WritableCache<T>
{
   val repository: RepositoryImpl<*, T> get() = uniformizer.repository
   val cac7er: Cac7er                   get() = uniformizer.repository.cac7er
   val fileName: String                 get() = uniformizer.fileName

   override fun get(time: Long, accessCount: Float): T {
      if (accessCount != 0.0f) {
         uniformizer.repository.launch(writerCoroutineDispatcher) {
            try {
               uniformizer.circulationRecord.add(time, accessCount)
               saveCirculationRecord(uniformizer)
            } catch (e: Exception) {
               // ignore
            }
         }
      }

      return uniformizer.content
   }

   override fun save(content: T) {
      uniformizer.content = content

      uniformizer.repository.launch(writerCoroutineDispatcher) {
         try {
            save(uniformizer)
            cac7er.autoGc()
         } catch (e: Exception) {
            // ignore
         }
      }
   }

   override fun addObserver(observer: (Cache<T>, T) -> Unit) {
      uniformizer.addObserver(observer)
   }

   override fun addObserver(owner: Any, observer: (Cache<T>, T) -> Unit) {
      uniformizer.addObserver(owner, observer)
   }

   override fun removeObserver(observer: (Cache<T>, T) -> Unit) {
      uniformizer.removeObserver(observer)
   }

   override fun toLazyCache() = LazyCacheImpl(uniformizer)
   override fun toWeakCache() = WeakCacheImpl(uniformizer)

   override fun equals(other: Any?): Boolean {
      if (other !is CacheImpl<*>) return false
      return uniformizer === other.uniformizer
   }

   override fun hashCode() = uniformizer.hashCode()
}
