package cac7er

internal class CacheImpl<T>(val uniformizer: Uniformizer<T>) : WritableCache<T> {
   val repository: RepositoryImpl<*, T> get() = uniformizer.repository
   val cac7er: Cac7er                   get() = uniformizer.repository.cac7er
   val fileName: String                 get() = uniformizer.fileName

   override fun get(time: Long, accessCount: Float): T {
      incrementCirculationRecordLazy(uniformizer, time, accessCount)

      return uniformizer.content
   }

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

   override fun toLazyCache() = LazyCacheImpl(uniformizer)
   override fun toWeakCache() = WeakCacheImpl(uniformizer)

   override fun equals(other: Any?): Boolean {
      if (other !is CacheImpl<*>) return false
      return uniformizer === other.uniformizer
   }

   override fun hashCode() = uniformizer.hashCode()
}
