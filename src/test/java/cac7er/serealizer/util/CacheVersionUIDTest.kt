package cac7er.serealizer.util

import cac7er.*

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import org.junit.Assert.*

import java.io.*
import cac7er.serializer.*
import cac7er.serializer.util.*

import kotlin.contracts.*

@RunWith(JUnit4::class)
class CacheVersionUIDTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @ExperimentalContracts
   @Test fun match() {
      val cac7er = buildCac7er("match", testDir) {}

      with (cac7er.getCacheOutput("match")) {
         writeCacheVersionUID(1)
         writeInt(3)
      }

      with (cac7er.getCacheInput("match")) {
         checkCacheVersionUID(1)
         assert(readInt() == 3)
      }
   }

   @ExperimentalContracts
   @Test fun mismatch() {
      val cac7er = buildCac7er("mismatch", testDir) {}

      with (cac7er.getCacheOutput("mismatch")) {
         writeCacheVersionUID(1)
         writeInt(3)
      }

      with (cac7er.getCacheInput("mismatch")) {
         try {
            checkCacheVersionUID(2)
            fail()
         } catch (e: IOException) {
            assert(e.message == "cache version UID doesn't match. expected 2 but 1")
         }
      }
   }

   private fun Cac7er.getCacheOutput(name: String): CacheOutput {
      val file = File(testDir, name)

      return CacheOutput(DataOutputStream(file.outputStream()), file, this)
   }

   private fun Cac7er.getCacheInput(name: String): CacheInput {
      val file = File(testDir, name)

      return CacheInput(DataInputStream(file.inputStream()), this)
   }
}
