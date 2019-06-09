package cac7er

/**
 * All Cache-like interfaces in Cac7er are Observable.
 *
 * @since 0.8.0
 */
interface Observable<out T> {
   /**
    * adds a function to observe this cache.
    *
    * If you are good at RxJava, you may prefer
    * [RxCac7er](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/RxCac7er)
    *
    * @since 0.3.0
    */
   fun addObserver(observer: (Cache<T>, T) -> Unit)

   /**
    * removes the observer. The name says it all.
    * @since 0.3.0
    */
   fun removeObserver(observer: (Cache<T>, T) -> Unit)
}
