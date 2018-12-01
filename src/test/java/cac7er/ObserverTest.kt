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

      cache.addObserver { receivedValues += it }

      cache.save(2)
      cache.save(5)

      assert(receivedValues == listOf(2, 5))
   }

   @Test fun removeTest() {
      val cache = intRepo.save("removeTest", 1)

      val receivedValues = mutableListOf<Int>()

      val observer: (Int) -> Unit = { receivedValues += it }

      cache.addObserver(observer)
      cache.save(2)
      cache.removeObserver(observer)
      cache.save(5)

      assert(receivedValues == listOf(2))
   }

   @Test fun observerViaRepo() {
      val cache = intRepo.save("observerViaRepo", 1)

      val receivedValues = mutableListOf<Int>()

      cache.addObserver { receivedValues += it }

      intRepo.save("observerViaRepo", 2)
      intRepo.save("anotherCache", 5)
      intRepo.save("observerViaRepo", 7)

      assert(receivedValues == listOf(2, 7))
   }

   @Test fun owner() {
      val cache = intRepo.save("owner", 1)

      val receivedValuesWithoutOwner = mutableListOf<Int>()
      val receivedValuesWithOwner    = mutableListOf<Int>()

      cache.addObserver { receivedValuesWithoutOwner += it }

      val owner = Any()
      cache.addObserver(owner) { receivedValuesWithOwner += it }

      System.gc()
      Thread.sleep(500L)

      cache.save(2)
      cache.save(5)

      assert(receivedValuesWithoutOwner == emptyList<Int>())
      assert(receivedValuesWithOwner    == listOf(2, 5))
   }

   @Test fun weakCacheObserverTest() {
      val cache = intRepo.save("weakCacheObserverTest", 1).toWeakCache()

      val receivedValues = mutableListOf<Int>()

      cache.addObserver { receivedValues += it }

      cache.save(2)
      cache.save(5)

      assert(receivedValues == listOf(2, 5))
   }
}
