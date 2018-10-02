package cac7er.serealizer.date

import cac7er.*

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import cac7er.serializer.*
import cac7er.serializer.date.*

import java.text.SimpleDateFormat
import java.util.*

@RunWith(JUnit4::class)
class DateTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @Test fun date() {
      val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")

      val date1 = dateFormat.parse("2017/09/3 01:00")
      val date2 = dateFormat.parse("2018/11/3 01:00")

      val cac7er = Cac7er("date", testDir) {}

      with (cac7er.getCacheOutput("date")) {
         writeDate(date1)
         writeDate(date2)
      }

      with (cac7er.getCacheInput("date")) {
         assert(readDate() == date1)
         assert(readDate() == date2)
      }
   }

   @Test fun dateWithTimeZone() {
      val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")

      val date1 = dateFormat.parse("2017/09/3 01:00")
      val date2 = dateFormat.parse("2018/11/3 01:00")

      val cac7er = Cac7er("dateWithTimeZone", testDir) {}

      with (cac7er.getCacheOutput("dateWithTimeZone")) {
         writeDateWithTimeZone(date1, TimeZone.getTimeZone("UTC"))
         writeDateWithTimeZone(date2, TimeZone.getTimeZone("UTC"))
      }

      with (cac7er.getCacheInput("dateWithTimeZone")) {
         assert(readDateWithTimeZone(TimeZone.getTimeZone("UTC")) == date1)

         assert(readDateWithTimeZone(TimeZone.getTimeZone("JST")).time ==
               date2.time + 9L * 60L * 60L * 1000L)
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
