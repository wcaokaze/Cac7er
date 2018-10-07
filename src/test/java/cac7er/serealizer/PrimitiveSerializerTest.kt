package cac7er.serealizer

import cac7er.*

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import org.junit.Assert.*

import java.io.*
import cac7er.serializer.*

import kotlin.contracts.*

@RunWith(JUnit4::class)
class PrimitiveSerializerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @ExperimentalContracts
   @Test fun boolean() {
      val cac7er = buildCac7er("boolean", testDir) {}

      with (cac7er.getCacheOutput("boolean")) {
         writeBoolean(true)
         writeBoolean(false)
      }

      with (cac7er.getCacheInput("boolean")) {
         assert( readBoolean())
         assert(!readBoolean())
      }
   }

   @ExperimentalContracts
   @Test fun byte() {
      val cac7er = buildCac7er("byte", testDir) {}
      val data = byteArrayOf(0, 1, 2, -1, Byte.MAX_VALUE, Byte.MIN_VALUE)

      with (cac7er.getCacheOutput("byte")) {
         for (b in data) {
            writeByte(b)
         }
      }

      with (cac7er.getCacheInput("byte")) {
         for (b in data) {
            assert(readByte() == b)
         }
      }
   }

   @ExperimentalContracts
   @Test fun char() {
      val cac7er = buildCac7er("char", testDir) {}
      val data = charArrayOf('a', 'A', 'あ', '\u0000', '\udc00')

      with (cac7er.getCacheOutput("char")) {
         for (c in data) {
            writeChar(c)
         }
      }

      with (cac7er.getCacheInput("char")) {
         for (c in data) {
            assert(readChar() == c)
         }
      }
   }

   @ExperimentalContracts
   @Test fun short() {
      val cac7er = buildCac7er("short", testDir) {}
      val data = shortArrayOf(0, 1, 2, -1, Short.MAX_VALUE, Short.MIN_VALUE)

      with (cac7er.getCacheOutput("short")) {
         for (s in data) {
            writeShort(s)
         }
      }

      with (cac7er.getCacheInput("short")) {
         for (s in data) {
            assert(readShort() == s)
         }
      }
   }

   @ExperimentalContracts
   @Test fun int() {
      val cac7er = buildCac7er("int", testDir) {}
      val data = intArrayOf(0, 1, 2, -1, Int.MAX_VALUE, Int.MIN_VALUE)

      with (cac7er.getCacheOutput("int")) {
         for (i in data) {
            writeInt(i)
         }
      }

      with (cac7er.getCacheInput("int")) {
         for (i in data) {
            assert(readInt() == i)
         }
      }
   }

   @ExperimentalContracts
   @Test fun long() {
      val cac7er = buildCac7er("long", testDir) {}
      val data = longArrayOf(0L, 1L, 2L, -1L, Long.MAX_VALUE, Long.MIN_VALUE)

      with (cac7er.getCacheOutput("long")) {
         for (l in data) {
            writeLong(l)
         }
      }

      with (cac7er.getCacheInput("long")) {
         for (l in data) {
            assert(readLong() == l)
         }
      }
   }

   @ExperimentalContracts
   @Test fun float() {
      val cac7er = buildCac7er("float", testDir) {}

      val data = floatArrayOf(
            .0f, .1f, 1.0f, 2.0f, -1.0f, -.0f,
            Float.MAX_VALUE, Float.MIN_VALUE,
            Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY,
            Float.NaN)

      with (cac7er.getCacheOutput("float")) {
         for (f in data) {
            writeFloat(f)
         }
      }

      with (cac7er.getCacheInput("float")) {
         for (f in data) {
            assertEquals(readFloat(), f, .0001f)
         }
      }
   }

   @ExperimentalContracts
   @Test fun double() {
      val cac7er = buildCac7er("double", testDir) {}

      val data = doubleArrayOf(
            .0, .1, 1.0, 2.0, -1.0, -.0,
            Double.MAX_VALUE, Double.MIN_VALUE,
            Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.NaN)

      with (cac7er.getCacheOutput("double")) {
         for (d in data) {
            writeDouble(d)
         }
      }

      with (cac7er.getCacheInput("double")) {
         for (d in data) {
            assertEquals(readDouble(), d, .0001)
         }
      }
   }

   @ExperimentalContracts
   @Test fun nullable() {
      val cac7er = buildCac7er("nullable", testDir) {}

      with (cac7er.getCacheOutput("nullable")) {
         writeNullable(null, CacheOutput::writeBoolean)
         writeNullable(true, CacheOutput::writeBoolean)
         writeNullable(null, CacheOutput::writeInt)
         writeNullable(   1, CacheOutput::writeInt)
      }

      with (cac7er.getCacheInput("nullable")) {
         assertEquals(null, readNullable(CacheInput::readBoolean))
         assertEquals(true, readNullable(CacheInput::readBoolean))
         assertEquals(null, readNullable(CacheInput::readInt))
         assertEquals(   1, readNullable(CacheInput::readInt))
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
