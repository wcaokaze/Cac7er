package cac7er

import java.io.*

/**
 * Cache which denotes an empty file.
 *
 * This can instantiate without any saving process, and can observe the file in
 * which the content will be written at some future time.
 *
 * Of course [Projector][cac7er.Projector] and RxCac7er are available.
 * This is so useful for asynchronous downloading.
 *
 * ```
 * fun fetchStatus(statusId: Long): FutureCache<Status> {
 *    launch(Dispatchers.IO) {
 *       // ...
 *       repository.save(statusId, status)
 *    }
 *
 *    return repository.getFuture(statusId)
 * }
 * ```
 *
 * @since 0.8.0
 */
interface FutureCache<out T> : Observable<T> {
   /**
    * returns the content is already saved, or null if not saved yet.
    * @since 0.8.0
    */
   fun getIfAlreadySaved(time: Long = System.currentTimeMillis(),
                         accessCount: Float = 0.0f): T?

   /**
    * Basically, FutureCache denotes an empty file. But Cac7er may have
    * the file by coincidence.
    *
    * @param time
    *   the time when the Cache is accessed. [Cac7er.gc] makes this a criteria
    *   to consider how the Cache is important.
    *
    * @param accessCount
    *   the number of access. The type is [Float] for flexibility, infinite and
    *   negative values also work well if you can utilize it. But `NaN` is
    *   invalid (will be ignored).
    *
    * @return the cached instance, or null if Cac7er does not have the cache.
    *
    * @throws IOException
    *
    * @throws ClassCastException
    *   when the cached instance is not an instance of [T]. In the strict sense,
    *   [ClassCastException] is thrown by a cast operation complemented by
    *   the compiler.
    *
    * @since 0.8.0
    */
   suspend fun load(time: Long, accessCount: Float = 0.0f): T?

   /**
    * loads the content in the background.
    *
    * Basically, FutureCache denotes an empty file. But Cac7er may have
    * the file by coincidence.
    *
    * This function returns Unit. [addObserver] to receive the loaded content.
    *
    * @since 0.8.0
    */
   fun loadAsync()
}
