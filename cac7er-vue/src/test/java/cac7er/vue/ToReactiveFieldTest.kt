package cac7er.vue

import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*
import org.junit.runners.*

import cac7er.*
import cac7er.serializer.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import java.io.*
import kotlin.Result
import kotlin.contracts.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ToReactiveFieldTest {
   companion object {
      private val TEST_DIR = File(".AsReactiveFieldTest")
   }

   private val mainThreadSurrogate = newSingleThreadContext("UI thread")

   private lateinit var intRepo: WritableRepository<String, Int>

   @Before fun setMainDispatcher() {
      Dispatchers.setMain(mainThreadSurrogate)
   }

   @After fun resetMainDispatcher() {
      Dispatchers.resetMain()
   }

   @Before fun createRepository() {
      TEST_DIR.mkdir()

      @OptIn(ExperimentalContracts::class)
      buildCac7er("AsReactiveFieldTest", TEST_DIR) {
         intRepo = createRepository("a", CacheOutput::writeInt, CacheInput::readInt)
      }
   }

   @After fun deleteTestDir() {
      TEST_DIR.deleteRecursively()
   }

   @Test fun observer() {
      val cache = intRepo.save("observer", 0)
      var i = -1
      cache.toReactiveField().addObserver { i = it.getOrThrow() }
      cache.save(1)
      Thread.sleep(50L)
      assertEquals(1, i)
   }

   @Test fun observerShouldNotCalled_onAddingObserver() {
      val cache = intRepo.save("observerShouldNotCalled_onAddingObserver", 0)
      cache.toReactiveField().addObserver { fail("observer should not called on added") }
      Thread.sleep(50L)
   }

   /*
   @Test fun connectToUpstream() {
      val cache = intRepo.save("connectToUpstream", 0)
      val rf = cache.toReactiveField()

      assertEquals(0, cache.observerCount)
      rf.addObserver {}
      assertEquals(1, cache.observerCount)
   }

   @Test fun disconnectFromUpstream() {
      val cache = intRepo.save("disconnectFromUpstream", 0)
      val rf = cache.toReactiveField()

      val observer: (Result<Int>) -> Unit = {}

      rf.addObserver(observer)
      assertEquals(1, cache.observerCount)
      rf.removeObserver(observer)
      assertEquals(0, cache.observerCount)
   }
   */

   @Test fun observerCount_increased() {
      val cache = intRepo.save("observerCount_increased", 0)
      val rf = cache.toReactiveField()

      repeat (4) { count ->
         assertEquals(count, rf.observerCount)
         rf.addObserver(Observer {})
      }
   }

   @Test fun removeObserver() {
      val cache = intRepo.save("removeObserver", 0)
      val rf = cache.toReactiveField()
      var i = -1
      val observer: (Result<Int>) -> Unit = { i = it.getOrThrow() }
      rf.addObserver(observer)
      cache.save(1)
      Thread.sleep(50L)
      rf.removeObserver(observer)
      cache.save(2)
      Thread.sleep(50L)
      assertEquals(1, i)
   }

   @Test fun removeObserver_fifo() {
      val cache = intRepo.save("removeObserver_fifo", 0)
      val rf = cache.toReactiveField()

      var i1 = -1
      var i2 = -1
      var i3 = -1
      val observer1: (Result<Int>) -> Unit = { i1 = it.getOrThrow() }
      val observer2: (Result<Int>) -> Unit = { i2 = it.getOrThrow() }
      val observer3: (Result<Int>) -> Unit = { i3 = it.getOrThrow() }

      rf.addObserver(observer1)
      rf.addObserver(observer2)
      rf.addObserver(observer3)

      cache.save(1)
      Thread.sleep(50L)
      rf.removeObserver(observer1)
      cache.save(2)
      Thread.sleep(50L)
      rf.removeObserver(observer2)
      cache.save(3)
      Thread.sleep(50L)
      rf.removeObserver(observer3)

      assertEquals(1, i1)
      assertEquals(2, i2)
      assertEquals(3, i3)
   }

   @Test fun removeObserver_lifo() {
      val cache = intRepo.save("removeObserver_lifo", 0)
      val rf = cache.toReactiveField()

      var i1 = -1
      var i2 = -1
      var i3 = -1
      val observer1: (Result<Int>) -> Unit = { i1 = it.getOrThrow() }
      val observer2: (Result<Int>) -> Unit = { i2 = it.getOrThrow() }
      val observer3: (Result<Int>) -> Unit = { i3 = it.getOrThrow() }

      rf.addObserver(observer1)
      rf.addObserver(observer2)
      rf.addObserver(observer3)

      cache.save(1)
      Thread.sleep(50L)
      rf.removeObserver(observer3)
      cache.save(2)
      Thread.sleep(50L)
      rf.removeObserver(observer2)
      cache.save(3)
      Thread.sleep(50L)
      rf.removeObserver(observer1)

      assertEquals(3, i1)
      assertEquals(2, i2)
      assertEquals(1, i3)
   }

   @Test fun removeObserver_random() {
      val cache = intRepo.save("removeObserver_random", 0)
      val rf = cache.toReactiveField()

      var i1 = -1
      var i2 = -1
      var i3 = -1
      val observer1: (Result<Int>) -> Unit = { i1 = it.getOrThrow() }
      val observer2: (Result<Int>) -> Unit = { i2 = it.getOrThrow() }
      val observer3: (Result<Int>) -> Unit = { i3 = it.getOrThrow() }

      rf.addObserver(observer1)
      rf.addObserver(observer2)
      rf.addObserver(observer3)

      cache.save(1)
      Thread.sleep(50L)
      rf.removeObserver(observer2)
      cache.save(2)
      Thread.sleep(50L)
      rf.removeObserver(observer3)
      cache.save(3)
      Thread.sleep(50L)
      rf.removeObserver(observer1)

      assertEquals(3, i1)
      assertEquals(1, i2)
      assertEquals(2, i3)
   }

   @Test fun observerCount_decreased_fifo() {
      val cache = intRepo.save("observerCount_decreased_fifo", 0)
      val rf = cache.toReactiveField()

      val observers = (0..3).map { Observer<Result<Int>> {} }

      for (o in observers) {
         rf.addObserver(o)
      }

      var count = 4

      for (o in observers) {
         assertEquals(count, rf.observerCount)
         rf.removeObserver(o)
         count--
      }
   }

   @Test fun observerCount_decreased_lifo() {
      val cache = intRepo.save("observerCount_decreased_lifo", 0)
      val rf = cache.toReactiveField()

      val observers = (0..3).map { Observer<Result<Int>> {} }

      for (o in observers) {
         rf.addObserver(o)
      }

      var count = 4

      for (o in observers.reversed()) {
         assertEquals(count, rf.observerCount)
         rf.removeObserver(o)
         count--
      }
   }

   @Test fun observerCount_decreased_random() {
      val cache = intRepo.save("observerCount_decreased_random", 0)
      val rf = cache.toReactiveField()

      val observers = (0..3).map { Observer<Result<Int>> {} }

      for (o in observers) {
         rf.addObserver(o)
      }

      var count = 4

      listOf(2, 3, 1)
            .map { observers[it] }
            .forEach { o ->
               assertEquals(count, rf.observerCount)
               rf.removeObserver(o)
               count--
            }
   }

   @Test fun addObserver_notDuplicated() {
      val cache = intRepo.save("addObserver_notDuplicated", 0)
      val rf = cache.toReactiveField()

      val observer: (Result<Int>) -> Unit = {}

      rf.addObserver(observer)
      rf.addObserver(observer)

      assertEquals(1, rf.observerCount)
   }

   @Test fun removeObserver_nopForNonAddedObserver() {
      val cache = intRepo.save("removeObserver_nopForNonAddedObserver", 0)
      val rf = cache.toReactiveField()

      val observer: (Result<Int>) -> Unit = {}

      rf.removeObserver(observer)
      assertEquals(0, rf.observerCount)
   }

   @Test fun distinct() {
      val cache = intRepo.save("distinct", 0)
      val rf = cache.toReactiveField()

      rf.addObserver { fail() }
      cache.save(0)
      Thread.sleep(50L)
   }

   /**
    * To waste kotlin optimizer.
    *
    * In Kotlin, lambda expressions that don't capture any values
    * (i.e. non-closure lambdas) are not instantiated twice.
    *
    * This is a wrapper to create 2 or more instances for the observers.
    */
   private class Observer<T>(private val o: (T) -> Unit) : (T) -> Unit {
      override fun invoke(p1: T) {
         o(p1)
      }
   }
}
