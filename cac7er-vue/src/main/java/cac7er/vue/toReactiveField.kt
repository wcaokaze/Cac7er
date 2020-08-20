package cac7er.vue

import cac7er.*
import kotlinx.coroutines.*
import vue.*

fun <T> Cache<T>.toReactiveField(): ReactiveField<T> = object : CacheFieldBase<T, T>() {
   override fun getCacheContent() = get()

   override fun addCacheObserver(observer: (Cache<T>, T) -> Unit) {
      addObserver(observer)
   }

   override fun removeCacheObserver(observer: (Cache<T>, T) -> Unit) {
      removeObserver(observer)
   }
}

fun <T> WeakCache<T>.toReactiveField(): ReactiveField<T?> = object : CacheFieldBase<T?, T>() {
   override fun getCacheContent() = get()

   override fun addCacheObserver(observer: (Cache<T>, T) -> Unit) {
      addObserver(observer)
   }

   override fun removeCacheObserver(observer: (Cache<T>, T) -> Unit) {
      removeObserver(observer)
   }
}

fun <T> LazyCache<T>.toReactiveField(): ReactiveField<T?> = object : CacheFieldBase<T?, T>() {
   override fun getCacheContent() = getIfAlreadyLoaded()

   override fun addCacheObserver(observer: (Cache<T>, T) -> Unit) {
      addObserver(observer)
      loadAsync()
   }

   override fun removeCacheObserver(observer: (Cache<T>, T) -> Unit) {
      removeObserver(observer)
   }
}

fun <T> FutureCache<T>.toReactiveField(): ReactiveField<T?> = object : CacheFieldBase<T?, T>() {
   override fun getCacheContent() = getIfAlreadySaved()

   override fun addCacheObserver(observer: (Cache<T>, T) -> Unit) {
      addObserver(observer)
      loadAsync()
   }

   override fun removeCacheObserver(observer: (Cache<T>, T) -> Unit) {
      removeObserver(observer)
   }
}

private abstract class CacheFieldBase<out T, out C>
   : ReactiveField<T>
      where C : T
{
   private var downstreams = emptyArray<(Result<T>) -> Unit>()

   private val cacheObserver = fun (_: Cache<C>, content: C) {
      GlobalScope.launch(Dispatchers.Main) {
         for (observer in downstreams) {
            observer(Result.success(content))
         }
      }
   }

   protected abstract fun getCacheContent(): T
   protected abstract fun addCacheObserver   (observer: (Cache<C>, C) -> Unit)
   protected abstract fun removeCacheObserver(observer: (Cache<C>, C) -> Unit)

   @Suppress("OverridingDeprecatedMember")
   override val `$vueInternal$value`: T
      get() = getCacheContent()

   override val observerCount get() = downstreams.size

   override fun addObserver(observer: (Result<T>) -> Unit) {
      if (containsObserver(observer)) { return }

      val shouldBind = observerCount == 0

      downstreams += observer

      if (shouldBind) {
         addCacheObserver(cacheObserver)
      }
   }

   override fun removeObserver(observer: (Result<T>) -> Unit) {
      val a = downstreams

      when (a.size) {
         0 -> return

         1 -> {
            if (a[0] === observer) {
               downstreams = emptyArray()
               removeCacheObserver(cacheObserver)
            }

            return
         }

         else -> {
            var i = 0

            while (true) {
               if (i >= a.size) { return }
               if (a[i] === observer) { break }
               i++
            }

            val newL = a.size - 1
            val newA = arrayOfNulls<(Result<T>) -> Unit>(newL)

            System.arraycopy(a,     0, newA, 0, i)
            System.arraycopy(a, i + 1, newA, i, newL - i)

            @Suppress("UNCHECKED_CAST")
            downstreams = newA as Array<(Result<T>) -> Unit>
         }
      }
   }

   private fun containsObserver(observer: (Result<T>) -> Unit): Boolean {
      for (o in downstreams) {
         if (o === observer) { return true }
      }

      return false
   }
}
