package cac7er.util

import java.lang.ref.*

/**
 * looks like a [Map] whose values are created automatically, and removed
 * automatically.
 *
 * If this Pool doesn't have the value, [get] will invoke [valueSupplier]. The
 * values are referenced as [SoftReference]. GC may collect them.
 *
 * @since 1.0.0
 */
class Pool<in K, out V>(private val valueSupplier: (K) -> V) {
   private val delegate = HashMap<K, SoftReferenceWithKey<K, V>>()
   private val referenceQueue = ReferenceQueue<V>()

   /** @since 1.0.0 */
   @Synchronized
   operator fun get(key: K): V? {
      var value = delegate[key]?.get()

      if (value != null) return value

      removeUnreachableReferences()

      value = valueSupplier(key)

      delegate[key] = SoftReferenceWithKey(key, value, referenceQueue)

      return value
   }

   private fun removeUnreachableReferences() {
      while (true) {
         @Suppress("UNCHECKED_CAST")
         val reference = referenceQueue.poll() as SoftReferenceWithKey<K, V>?
               ?: return

         delegate.remove(reference.key)
      }
   }

   private class SoftReferenceWithKey<out K, V>
         constructor(val key: K, value: V, referenceQueue: ReferenceQueue<V>)
         : SoftReference<V>(value, referenceQueue)
}
