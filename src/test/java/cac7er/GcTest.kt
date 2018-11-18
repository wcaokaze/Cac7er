package cac7er

import cac7er.serializer.*
import cac7er.serializer.collection.*
import kotlinx.coroutines.*
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import kotlin.contracts.*

@RunWith(JUnit4::class)
class GcTest {
   private companion object {
      val TEST_DIR = File(".cac7erTest")

      private var count = 0

      /**
       * Cac7er caches the instances of [Cache]. So each test must use identified
       * [File]s for [Cache]s.
       */
      fun supplyFileName() = count++.toString()
   }

   private val garbage = IntRange(0, 256).toList()

   @Before fun createTestDir() {
      TEST_DIR.mkdir()
   }

   @After fun deleteTestDir() {
      TEST_DIR.deleteRecursively()
   }

   @ExperimentalContracts
   @Test fun basic() = runBlocking {
      val repo: WritableRepository<String, List<Int>>

      val cac7er = buildCac7er("basic", TEST_DIR) {
         repo = createRepository("basicA",
               { writeList(it, CacheOutput::writeInt) },
               { readList(CacheInput::readInt) })
      }

      val fileName1 = supplyFileName()
      val fileName2 = supplyFileName()

      val cache1 = repo.save(fileName1, garbage)
      val cache2 = repo.save(fileName2, garbage)

      cache1.get(1.0f)

      delay(300L)

      val expectedLiveFiles = listOf(
            getCacheFile("basicA", fileName1))

      cac7er.gc(fileSizeFor(expectedLiveFiles)).join()

      assert(expectedLiveFiles isConsistentWith TEST_DIR) { getCacheFilesStr() }
   }

   @ExperimentalContracts
   @Test fun depend() = runBlocking {
      class Foo(@Suppress("UNUSED") val cache: Cache<List<Int>>)

      val repoA: WritableRepository<String, List<Int>>
      val repoB: WritableRepository<String, Foo>

      val cac7er = buildCac7er("depend", TEST_DIR) {
         repoA = createRepository("dependA",
               { writeList(it, CacheOutput::writeInt) },
               { readList(CacheInput::readInt) })

         repoB = createRepository("dependB",
               { writeCache(it.cache) },
               { Foo(readCache()) })
      }

      val fileName1 = supplyFileName()
      val fileName2 = supplyFileName()
      val fileName3 = supplyFileName()

      val cache1 = repoA.save(fileName1, garbage)
      val cache2 = repoB.save(fileName2, Foo(cache1))
      val cache3 = repoA.save(fileName3, garbage)

      cache2.get(2.0f)
      cache3.get(1.0f)

      delay(300L)

      /*
       * cache3.importance > cache1.importance but cache1 will survive. Because
       * cache1 is depended by cache2 and cache2.importance > cache3.importance.
       */
      val expectedLiveFiles = listOf(
            getCacheFile("dependA", fileName1),
            getCacheFile("dependB", fileName2))

      cac7er.gc(fileSizeFor(expectedLiveFiles)).join()

      assert(expectedLiveFiles isConsistentWith TEST_DIR) { getCacheFilesStr() }
   }

   @ExperimentalContracts
   @Test fun deletingAllSameImportanceFiles() = runBlocking {
      val repo: WritableRepository<String, String>

      val cac7er = buildCac7er("deletingAllSameImportanceFiles", TEST_DIR) {
         repo = createRepository("deletingAllSameImportanceFilesA",
               CacheOutput::writeString, CacheInput::readString)
      }

      val fileNames = IntRange(0, 127).map { supplyFileName() }

      for (fileName in fileNames) {
         repo.save(fileName, "wcaokaze")
      }

      delay(300L)

      val files = fileNames.map {
         getCacheFile("deletingAllSameImportanceFilesA", it)
      }

      cac7er.gc(fileSizeFor(files) / 2).join()

      assert(emptyList<File>() isConsistentWith TEST_DIR) { getCacheFilesStr() }
   }

   @ExperimentalContracts
   @Test fun infinite() = runBlocking {
      val repo: WritableRepository<String, String>

      val cac7er = buildCac7er("infinite", TEST_DIR) {
         repo = createRepository("infiniteA",
               CacheOutput::writeString, CacheInput::readString)
      }

      val fileName1 = supplyFileName()
      val fileName2 = supplyFileName()

      val cache1 = repo.save(fileName1, "wcaokaze")
      val cache2 = repo.save(fileName2, "wcaokaze")

      delay(300L)

      cache1.get(114514.0f)
      cache2.get(Float.POSITIVE_INFINITY)

      val expectedLiveFiles = listOf(getCacheFile("infiniteA", fileName2))

      cac7er.gc(fileSizeFor(expectedLiveFiles)).join()

      assert(expectedLiveFiles isConsistentWith TEST_DIR) { getCacheFilesStr() }
   }

   @ExperimentalContracts
   @Test fun multiDepended() = runBlocking {
      class Foo(@Suppress("UNUSED") val cache: Cache<String>)

      val repoA: WritableRepository<String, String>
      val repoB: WritableRepository<String, Foo>

      val cac7er = buildCac7er("multiDepended", TEST_DIR) {
         repoA = createRepository("multiDependedA",
               CacheOutput::writeString, CacheInput::readString)

         repoB = createRepository("multiDependedB",
               { writeCache(it.cache) },
               { Foo(readCache()) })
      }

      val fileName1 = supplyFileName()
      val fileName2 = supplyFileName()
      val fileName3 = supplyFileName()

      val cache1 = repoA.save(fileName1, "wcaokaze")
      val cache2 = repoB.save(fileName2, Foo(cache1))
      val cache3 = repoB.save(fileName3, Foo(cache1))

      cache2.get(2.0f)
      cache3.get(3.0f)

      delay(300L)

      val expectedLiveFiles = listOf(
            getCacheFile("multiDependedA", fileName1),
            getCacheFile("multiDependedB", fileName3))

      cac7er.gc(fileSizeFor(expectedLiveFiles)).join()

      assert(expectedLiveFiles isConsistentWith TEST_DIR) { getCacheFilesStr() }
   }

   @ExperimentalContracts
   @Test fun circularReference() = runBlocking {
      class Foo(@Suppress("UNUSED") val cache: Cache<*>)

      class Bar(@Suppress("UNUSED") val cache1: Cache<*>?,
                @Suppress("UNUSED") val cache2: Cache<*>?)

      val repoA: WritableRepository<String, String>
      val repoB: WritableRepository<String, Foo>
      val repoC: WritableRepository<String, Bar>

      val cac7er = buildCac7er("circularReference", TEST_DIR) {
         repoA = createRepository("circularReferenceA",
               CacheOutput::writeString, CacheInput::readString)

         repoB = createRepository("circularReferenceB",
               { writeCache(it.cache) },
               { Foo(readCache<Any?>()) })

         repoC = createRepository("circularReferenceC",
               { writeNullable(it.cache1) { writeCache(it) }
                 writeNullable(it.cache2) { writeCache(it) } },
               { Bar(readNullable { readCache<Any?>() },
                     readNullable { readCache<Any?>() }) })
      }

      /*
       * +-----------------+
       * | cache4          |
       * | importance: 0.0 |
       * +-----------------+
       *       |
       *       V
       * +-----------------+       +-----------------+
       * | cache2          |  -->  | cache3          |
       * | importance: 0.0 |  <--  | importance: 1.0 |
       * +-----------------+       +-----------------+
       *       |
       *       V
       * +-----------------+
       * | cache1          |
       * | "wcaokaze"      |
       * | importance: 0.0 |
       * +-----------------+
       */
      val fileName1 = supplyFileName()
      val fileName2 = supplyFileName()
      val fileName3 = supplyFileName()
      val fileName4 = supplyFileName()

      val cache1 = repoA.save(fileName1, "wcaokaze")
      val cache2 = repoC.save(fileName2, Bar(cache1, null))
      val cache3 = repoB.save(fileName3, Foo(cache2))
      val cache4 = repoB.save(fileName4, Foo(cache2))

      cache2.save(Bar(cache1, cache3))

      cache3.get(1.0f)

      delay(300L)

      val expectedLiveFiles = listOf(
            getCacheFile("circularReferenceA", fileName1),
            getCacheFile("circularReferenceC", fileName2),
            getCacheFile("circularReferenceB", fileName3))

      cac7er.gc(fileSizeFor(expectedLiveFiles)).join()

      assert(expectedLiveFiles isConsistentWith TEST_DIR) { getCacheFilesStr() }
   }

   private fun fileSizeFor(fileList: List<File>)
         = fileList.map { it.length() }.sum() + 10L

   private fun getCacheFile(repositoryName: String, fileName: String): File {
      val repoDir = File(TEST_DIR, repositoryName)

      return File(repoDir, fileName)
   }

   private fun getCacheFilesStr()
         = getCacheFiles(TEST_DIR).joinToString(", ") { it.name }

   private fun getCacheFiles(directory: File): List<File> {
      return directory.listFiles()
            .filter { it.isDirectory } // filter Repositories
            .map { it.listFiles().toList() }
            .flatten()
   }

   private infix fun Iterable<File>.isConsistentWith(directory: File): Boolean {
      val cacheFiles = getCacheFiles(directory)

      return all { it in cacheFiles } && cacheFiles.all { it in this }
   }
}
