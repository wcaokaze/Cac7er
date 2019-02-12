package cac7er

import kotlinx.coroutines.*

internal class WeakCacheImpl<T>(private val uniformizer: Uniformizer<T>)
      : WritableWeakCache<T>
{
   val repository: RepositoryImpl<*, T> get() = uniformizer.repository
   val cac7er: Cac7er                   get() = uniformizer.repository.cac7er
   val fileName: String                 get() = uniformizer.fileName

   override fun get(time: Long, accessCount: Float): T? {
      if (uniformizer.state == Uniformizer.State.DELETED) return null

      if (accessCount != .0f) {
         uniformizer.repository.launch(writerCoroutineDispatcher + SupervisorJob()) {
            try {
               uniformizer.circulationRecord.add(time, accessCount)
               saveCirculationRecord(uniformizer)
            } catch (e: Exception) {
               // ignore
            }
         }
      }

      return uniformizer.weakContent
   }

   override fun save(content: T) {
      uniformizer.content = content

      uniformizer.repository.launch(writerCoroutineDispatcher + SupervisorJob()) {
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

   override fun removeObserver(observer: (Cache<T>, T) -> Unit) {
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

   override fun equals(other: Any?): Boolean {
      if (other !is WeakCacheImpl<*>) return false
      return uniformizer === other.uniformizer
   }

   override fun hashCode() = uniformizer.hashCode()
}
