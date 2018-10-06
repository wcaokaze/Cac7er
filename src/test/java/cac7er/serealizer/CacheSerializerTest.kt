package cac7er.serealizer

import cac7er.*

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import org.junit.Assert.*

import java.io.*
import cac7er.serializer.*
import kotlinx.coroutines.experimental.*

@RunWith(JUnit4::class)
class CacheSerializerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @Test fun cache() {
      lateinit var repo: WritableRepository<Int, String>

      val cac7er = Cac7er("cache", testDir) {
         repo = createRepository("repo",
               CacheOutput::writeString, CacheInput::readString)
      }

      val cache = repo.save(0, "wcaokaze")

      with (cac7er.getCacheOutput("anotherRepo")) {
         writeCache(cache)
      }

      val readCache = with (cac7er.getCacheInput("anotherRepo")) {
         readCache<String>()
      }

      assert(readCache.get() == cache.get())

      cache.save("ezakoacw")

      assert(readCache.get() == cache.get())
   }

   @Test fun lazyCache() = runBlocking {
      lateinit var repo: WritableRepository<Int, String>

      val cac7er = Cac7er("lazyCache", testDir) {
         repo = createRepository("repo",
               CacheOutput::writeString, CacheInput::readString)
      }

      val cache = repo.save(0, "wcaokaze").toLazyCache()

      with (cac7er.getCacheOutput("anotherRepo")) {
         writeLazyCache(cache)
      }

      val readCache = with (cac7er.getCacheInput("anotherRepo")) {
         readLazyCache<String>()
      }

      assert(readCache.get() == cache.get())

      cache.save("ezakoacw")

      assert(readCache.get() == cache.get())
   }

   @Test fun weakCache() {
      lateinit var repo: WritableRepository<Int, String>

      val cac7er = Cac7er("weakCache", testDir) {
         repo = createRepository("repo",
               CacheOutput::writeString, CacheInput::readString)
      }

      val cache = repo.save(0, "wcaokaze").toWeakCache()

      with (cac7er.getCacheOutput("anotherRepo")) {
         writeWeakCache(cache)
      }

      val readCache = with (cac7er.getCacheInput("anotherRepo")) {
         readWeakCache<String>()
      }

      assert(readCache.get() == cache.get())

      cache.save("ezakoacw")

      assert(readCache.get() == cache.get())
   }

   private fun Cac7er.getCacheOutput(name: String): CacheOutput {
      val file = File(testDir, name)

      return CacheOutput(RandomAccessFile(file, "rw"), file, this)
   }

   private fun Cac7er.getCacheInput(name: String): CacheInput {
      val file = File(testDir, name)

      return CacheInput(RandomAccessFile(file, "r"), this)
   }
}
