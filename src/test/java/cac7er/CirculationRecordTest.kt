package cac7er

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import java.io.*

@RunWith(JUnit4::class)
class CirculationRecordTest {
   @Test fun terminal() {
      val record1 = recordOf()
      val record2 = recordOf()

      assert(record1 == record2)
   }

   @Test fun notEqual() {
      val record1 = recordOf()
      val record2 = recordOf(1 to 1.0f)

      assert(record1 != record2)
   }

   @Test fun singleSection() {
      val record1 = recordOf(1 to 1.0f)
      val record2 = recordOf(1 to 1.0f)

      assert(record1 == record2)
   }

   @Test fun multiSections() {
      val record1 = recordOf(1 to 1.0f, 2 to 2.0f)
      val record2 = recordOf(1 to 1.0f, 2 to 2.0f)

      assert(record1 == record2)
   }

   @Test fun discrete() {
      val record1 = recordOf(1 to 1.0f, 3 to 2.0f)
      val record2 = recordOf(1 to 1.0f, 3 to 2.0f)

      assert(record1 == record2)
   }

   @Test fun add() {
      val record1 = recordOf(1 to 1.0f, 1 to 2.0f)
      val record2 = recordOf(1 to 3.0f)

      assert(record1 == record2)
   }

   @Test fun upstream() {
      val record1 = recordOf(1 to 1.0f, 2 to 2.0f)
      record1.addSection(1, 2.0f)

      val record2 = recordOf(1 to 3.0f, 2 to 2.0f)

      assert(record1 == record2)
   }

   @Test fun insert() {
      val record1 = recordOf(1 to 1.0f, 3 to 3.0f)
      record1.addSection(2, 2.0f)

      val record2 = recordOf(1 to 1.0f, 2 to 2.0f, 3 to 3.0f)

      assert(record1 == record2)
   }

   @Test fun drop() {
      val record1 = recordOf()

      for (i in 0..256) {
         record1.addSection(i, i.toFloat())
      }

      val record2 = recordOf()

      for (i in 1..256) {
         record2.addSection(i, i.toFloat())
      }

      assert(record1 == record2)
   }

   @Test fun serialize() {
      val file = RandomAccessFile("CirculationRecordTest", "rw")

      val record1 = recordOf(1 to 1.0f, 2 to 2.0f, 3 to 3.0f, 4 to 4.0f, 5 to 5.0f)
      record1.writeTo(file)

      file.seek(0L)
      val record2 = CirculationRecord.readFrom(file)

      assert(record1 == record2)
   }

   private fun CirculationRecord.addSection(period: Int, accessCount: Float) {
      add(CirculationRecord.startTimeOf(period), accessCount)
   }

   private fun recordOf(vararg section: Pair<Int, Float>): CirculationRecord {
      val record = CirculationRecord()

      for ((period, accessCount) in section) {
         record.add(CirculationRecord.startTimeOf(period), accessCount)
      }

      return record
   }
}
