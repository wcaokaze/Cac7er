package cac7er.serealizer

import cac7er.*

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import cac7er.serializer.*

@RunWith(JUnit4::class)
class StringSerializerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @Test fun string() {
      val cac7er = Cac7er("string", testDir) {}

      with (cac7er.getCacheOutput("string")) {
         writeString("wcaokaze")
         writeString("2wiqua")
      }

      with (cac7er.getCacheInput("string")) {
         assert(readString() == "wcaokaze")
         assert(readString() == "2wiqua")
      }
   }

   @Test fun utf8() {
      val cac7er = Cac7er("string", testDir) {}

      with (cac7er.getCacheOutput("string")) {
         writeUtf8("wcaokaze")
         writeUtf8("2wiqua")
      }

      with (cac7er.getCacheInput("string")) {
         assert(readUtf8() == "wcaokaze")
         assert(readUtf8() == "2wiqua")
      }
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
