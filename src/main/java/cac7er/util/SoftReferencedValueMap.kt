package cac7er.util

import java.lang.ref.*

/**
 * Map whose values are referenced as [SoftReference]. This doesn't implement
 * [MutableMap] because it's peculiar.
 *
 * @since 1.0.0
 */
class SoftReferencedValueMap<in K, V> {
   private val delegate = HashMap<K, SoftReferenceWithKey<K, V>>()
   private val referenceQueue = ReferenceQueue<V>()

   /** @since 1.0.0 */
   @Synchronized
   operator fun get(key: K): V? = delegate[key]?.get()

   /** @since 1.0.0 */
   @Synchronized
   operator fun set(key: K, value: V): V? {
      removeUnreachableReferences()

      return delegate
            .put(key, SoftReferenceWithKey(key, value, referenceQueue))
            ?.get()
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
