package cac7er

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import cac7er.serializer.*

import kotlin.contracts.*

@RunWith(JUnit4::class)
class ObserverTest {
   private val testDir = File(".ObserverTest")
   private val intRepo: WritableRepository<String, Int>

   init {
      @UseExperimental(ExperimentalContracts::class)
      buildCac7er("ObserverTest", testDir) {
         intRepo = createRepository("a",
               CacheOutput::writeInt, CacheInput::readInt)
      }
   }

   @Test fun observerTest() {
      val cache = intRepo.save("observerTest", 1)

      val receivedValues = mutableListOf<Int>()

      cache.addObserver { _, content -> receivedValues += content }

      cache.save(2)
      cache.save(5)

      assert(receivedValues == listOf(2, 5))
   }

   @Test fun removeTest() {
      val cache = intRepo.save("removeTest", 1)

      val receivedValues = mutableListOf<Int>()

      val observer: (Cache<Int>, Int) -> Unit = { _, content -> receivedValues += content }

      cache.addObserver(observer)
      cache.save(2)
      cache.removeObserver(observer)
      cache.save(5)

      assert(receivedValues == listOf(2))
   }

   @Test fun observerViaRepo() {
      val cache = intRepo.save("observerViaRepo", 1)

      val receivedValues = mutableListOf<Int>()

      cache.addObserver { _, content -> receivedValues += content }

      intRepo.save("observerViaRepo", 2)
      intRepo.save("anotherCache", 5)
      intRepo.save("observerViaRepo", 7)

      assert(receivedValues == listOf(2, 7))
   }

   @Test fun weakCacheObserverTest() {
      val cache = intRepo.save("weakCacheObserverTest", 1).toWeakCache()

      val receivedValues = mutableListOf<Int>()

      cache.addObserver { _, cache -> receivedValues += cache }

      cache.save(2)
      cache.save(5)

      assert(receivedValues == listOf(2, 5))
   }
}
