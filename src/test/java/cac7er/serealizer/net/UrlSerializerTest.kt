package cac7er.serealizer.net

import cac7er.*

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import cac7er.serializer.*
import cac7er.serializer.net.*

import java.net.URL

import kotlin.contracts.*

@RunWith(JUnit4::class)
class UrlSerializerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @ExperimentalContracts
   @Test fun url() {
      val url1 = URL("http://2wiqua.wcaokaze.com/")

      val url2 = URL("https://play.google.com/store/apps/details?" +
            "id=com.wcaokaze.android.twiqua")

      val cac7er = buildCac7er("url", testDir) {}

      with (cac7er.getCacheOutput("url")) {
         writeUrl(url1)
         writeUrl(url2)
      }

      with (cac7er.getCacheInput("url")) {
         assert(readUrl() == url1)
         assert(readUrl() == url2)
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
