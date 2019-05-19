package cac7er

/**
 * Cache which denotes an empty file.
 *
 * This can instantiate without any saving process, and can observe the file in
 * which the content will be written at some future time.
 *
 * Of course [Projector][cac7er.projector.Projector] and RxCac7er are available.
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
interface FutureCache<out T> {
   /**
    * adds a function to observe this cache.
    *
    * If you are good at RxJava, you may prefer
    * [RxCac7er](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/RxCac7er)
    *
    * @since 0.8.0
    */
   fun addObserver(observer: (Cache<T>, T) -> Unit)

   /**
    * removes the observer. The name says it all.
    * @since 0.8.0
    */
   fun removeObserver(observer: (Cache<T>, T) -> Unit)
}
