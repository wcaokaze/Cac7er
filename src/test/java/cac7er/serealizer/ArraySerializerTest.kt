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
class ArraySerializerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @ExperimentalContracts
   @Test fun booleanArray() {
      val cac7er = buildCac7er("booleanArray", testDir) {}
      val data = booleanArrayOf(true, true, false)

      with (cac7er.getCacheOutput("booleanArray")) {
         writeBooleanArray(data)
      }

      with (cac7er.getCacheInput("booleanArray")) {
         assertArrayEquals(readBooleanArray(), data)
      }
   }

   @ExperimentalContracts
   @Test fun byteArray() {
      val cac7er = buildCac7er("byteArray", testDir) {}
      val data = byteArrayOf(0, 1, 2, -1, Byte.MAX_VALUE, Byte.MIN_VALUE)

      with (cac7er.getCacheOutput("byteArray")) {
         writeByteArray(data)
      }

      with (cac7er.getCacheInput("byteArray")) {
         assertArrayEquals(readByteArray(), data)
      }
   }

   @ExperimentalContracts
   @Test fun charArray() {
      val cac7er = buildCac7er("charArray", testDir) {}
      val data = charArrayOf('a', 'A', 'あ', '\u0000', '\udc00')

      with (cac7er.getCacheOutput("charArray")) {
         writeCharArray(data)
      }

      with (cac7er.getCacheInput("charArray")) {
         assertArrayEquals(readCharArray(), data)
      }
   }

   @ExperimentalContracts
   @Test fun shortArray() {
      val cac7er = buildCac7er("shortArray", testDir) {}
      val data = shortArrayOf(0, 1, 2, -1, Short.MAX_VALUE, Short.MIN_VALUE)

      with (cac7er.getCacheOutput("shortArray")) {
         writeShortArray(data)
      }

      with (cac7er.getCacheInput("shortArray")) {
         assertArrayEquals(readShortArray(), data)
      }
   }

   @ExperimentalContracts
   @Test fun intArray() {
      val cac7er = buildCac7er("intArray", testDir) {}
      val data = intArrayOf(0, 1, 2, -1, Int.MAX_VALUE, Int.MIN_VALUE)

      with (cac7er.getCacheOutput("intArray")) {
         writeIntArray(data)
      }

      with (cac7er.getCacheInput("intArray")) {
         assertArrayEquals(readIntArray(), data)
      }
   }

   @ExperimentalContracts
   @Test fun longArray() {
      val cac7er = buildCac7er("longArray", testDir) {}
      val data = longArrayOf(0L, 1L, 2L, -1L, Long.MAX_VALUE, Long.MIN_VALUE)

      with (cac7er.getCacheOutput("longArray")) {
         writeLongArray(data)
      }

      with (cac7er.getCacheInput("longArray")) {
         assertArrayEquals(readLongArray(), data)
      }
   }

   @ExperimentalContracts
   @Test fun floatArray() {
      val cac7er = buildCac7er("floatArray", testDir) {}

      val data = floatArrayOf(
            .0f, .1f, 1.0f, 2.0f, -1.0f, -.0f,
            Float.MAX_VALUE, Float.MIN_VALUE,
            Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY,
            Float.NaN)

      with (cac7er.getCacheOutput("floatArray")) {
         writeFloatArray(data)
      }

      with (cac7er.getCacheInput("floatArray")) {
         val readData = readFloatArray()

         assert(readData.size == data.size)

         for ((actual, expected) in readData zip data) {
            assertEquals(actual, expected, .0001f)
         }
      }
   }

   @ExperimentalContracts
   @Test fun doubleArray() {
      val cac7er = buildCac7er("doubleArray", testDir) {}

      val data = doubleArrayOf(
            .0, .1, 1.0, 2.0, -1.0, -.0,
            Double.MAX_VALUE, Double.MIN_VALUE,
            Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.NaN)

      with (cac7er.getCacheOutput("doubleArray")) {
         writeDoubleArray(data)
      }

      with (cac7er.getCacheInput("doubleArray")) {
         val readData = readDoubleArray()

         assert(readData.size == data.size)

         for ((actual, expected) in readData zip data) {
            assertEquals(actual, expected, .0001)
         }
      }
   }

   @ExperimentalContracts
   @Test fun genericArray() {
      val cac7er = buildCac7er("genericArray", testDir) {}
      val data = arrayOf("apple", "banana", "cherry")

      with (cac7er.getCacheOutput("genericArray")) {
         writeArray(data, CacheOutput::writeString)
      }

      with (cac7er.getCacheInput("genericArray")) {
         assertArrayEquals(readArray(CacheInput::readString), data)
      }
   }

   @ExperimentalContracts
   @Test fun nullableArray() {
      val cac7er = buildCac7er("nullableArray", testDir) {}
      val data = arrayOf("apple", "banana", null)

      with (cac7er.getCacheOutput("nullableArray")) {
         writeArray(data) { writeNullable(it, CacheOutput::writeString) }
      }

      with (cac7er.getCacheInput("nullableArray")) {
         val readData = readArray { readNullable(CacheInput::readString) }
         assertArrayEquals(readData, data)
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
