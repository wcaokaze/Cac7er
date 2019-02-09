package cac7er

import kotlinx.coroutines.*

internal class LazyCacheImpl<T>(private val uniformizer: Uniformizer<T>)
      : WritableLazyCache<T>
{
   val repository: RepositoryImpl<*, T> get() = uniformizer.repository
   val cac7er: Cac7er                   get() = uniformizer.repository.cac7er
   val fileName: String                 get() = uniformizer.fileName

   override suspend fun get(time: Long, accessCount: Float): T {
      uniformizer.loadIfNecessary()

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

      return uniformizer.content
   }

   override val hasContent: Boolean
      get() = uniformizer.state == Uniformizer.State.INITIALIZED

   override fun getIfAlreadyLoaded(time: Long, accessCount: Float): T? {
      if (uniformizer.state != Uniformizer.State.INITIALIZED) return null

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

      return uniformizer.content
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

   override fun addObserver(owner: Any, observer: (Cache<T>, T) -> Unit) {
      uniformizer.addObserver(owner, observer)
   }

   override fun removeObserver(observer: (Cache<T>, T) -> Unit) {
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

   override fun equals(other: Any?): Boolean {
      if (other !is LazyCacheImpl<*>) return false
      return uniformizer === other.uniformizer
   }

   override fun hashCode() = uniformizer.hashCode()
}
