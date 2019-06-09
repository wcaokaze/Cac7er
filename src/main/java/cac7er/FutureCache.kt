package cac7er

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
    * @since 0.8.0
    */
   fun getIfAlreadySaved(time: Long = System.currentTimeMillis(),
                         accessCount: Float = 0.0f): T?
}
