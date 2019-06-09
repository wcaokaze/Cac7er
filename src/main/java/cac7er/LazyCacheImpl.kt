package cac7er

internal class LazyCacheImpl<T>(val uniformizer: Uniformizer<T>)
      : WritableLazyCache<T>, FutureCache<T>
{
   val repository: RepositoryImpl<*, T> get() = uniformizer.repository
   val cac7er: Cac7er                   get() = uniformizer.repository.cac7er
   val fileName: String                 get() = uniformizer.fileName

   override suspend fun get(time: Long, accessCount: Float): T {
      uniformizer.loadIfNecessary()
      incrementCirculationRecordLazy(uniformizer, time, accessCount)

      return uniformizer.content
   }

   override val hasContent: Boolean
      get() = uniformizer.state == Uniformizer.State.INITIALIZED

   override fun getIfAlreadyLoaded(time: Long, accessCount: Float): T? {
      if (uniformizer.state != Uniformizer.State.INITIALIZED) return null

      incrementCirculationRecordLazy(uniformizer, time, accessCount)

      return uniformizer.content
   }

   override fun getIfAlreadySaved(time: Long, accessCount: Float): T?
         = getIfAlreadyLoaded(time, accessCount)

   override fun save(content: T) {
      uniformizer.content = content

      saveLazy(uniformizer)
   }

   override fun addObserver(observer: (Cache<T>, T) -> Unit) {
      uniformizer.addObserver(observer)
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
