package cac7er

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import cac7er.serializer.*

@RunWith(JUnit4::class)
class IndexerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @Test fun basicBuild() {
      val cac7er = Cac7er("basicBuild", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("a"))
   }

   @Test fun appendBuild() {
      Cac7er("appendBuild", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = Cac7er("appendBuild", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("a", "b"))
   }

   @Test fun misorderAdding() {
      Cac7er("misorderAdding", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = Cac7er("misorderAdding", testDir) {
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("a", "b"))
   }

   @Test fun removed() {
      Cac7er("removed", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = Cac7er("removed", testDir) {
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf(null, "b"))
   }

   @Test fun delegatee() {
      val delegatee = Cac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = Cac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("b", "a"))
   }

   @Test fun repositoryNameDeprecated() {
      val delegatee = Cac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = Cac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("a", "a"))
   }

   @Test fun addingDelegateeRepository() {
      var delegatee = Cac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      Cac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
      }

      delegatee = Cac7er("delegatee", testDir) {
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = Cac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("c", "a", "b"))
   }

   @Test fun removingDelegateeRepository() {
      var delegatee = Cac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      Cac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
      }

      delegatee = Cac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = Cac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("c", "a", null))
   }

   @Test fun complicated() {
      var delegatee = Cac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      Cac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("d", CacheOutput::writeInt, CacheInput::readInt)
      }

      delegatee = Cac7er("delegatee", testDir) {
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = Cac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("e", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("d", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("d", "a", null, "e", "c"))
   }
}
