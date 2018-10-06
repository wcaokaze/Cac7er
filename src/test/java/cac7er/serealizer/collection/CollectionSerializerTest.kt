package cac7er.serealizer.collection

import cac7er.*

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import cac7er.serializer.*
import cac7er.serializer.collection.*

@RunWith(JUnit4::class)
class CollectionSerializerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @Test fun list() {
      val cac7er = Cac7er("list", testDir) {}
      val data = listOf("wcaokaze", "was", "slain", "by", "Zombie")

      with (cac7er.getCacheOutput("list")) {
         writeList(data, CacheOutput::writeString)
      }

      with (cac7er.getCacheInput("list")) {
         val read = readList(CacheInput::readString)
         assert(read == data)
      }
   }

   @Test fun map() {
      val cac7er = Cac7er("map", testDir) {}
      val data = mapOf('a' to "apple", 'b' to "banana", 'c' to "cherry")

      with (cac7er.getCacheOutput("map")) {
         writeMap(data, CacheOutput::writeChar, CacheOutput::writeString)
      }

      with (cac7er.getCacheInput("map")) {
         val read = readMap(CacheInput::readChar, CacheInput::readString)
         assert(read == data)
      }
   }

   @Test fun set() {
      val cac7er = Cac7er("set", testDir) {}
      val data = setOf("wcaokaze", "was", "slain", "by", "Zombie")

      with (cac7er.getCacheOutput("set")) {
         writeSet(data, CacheOutput::writeString)
      }

      with (cac7er.getCacheInput("set")) {
         val read = readSet(CacheInput::readString)
         assert(read == data)
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
