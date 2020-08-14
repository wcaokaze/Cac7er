package cac7er

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import cac7er.serializer.*

import kotlin.contracts.*

@RunWith(JUnit4::class)
class IndexerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      testDir.deleteRecursively()
   }

   @ExperimentalContracts
   @Test fun basicBuild() {
      val cac7er = buildCac7er("basicBuild", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("a"))
   }

   @ExperimentalContracts
   @Test fun appendBuild() {
      buildCac7er("appendBuild", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = buildCac7er("appendBuild", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("a", "b"))
   }

   @ExperimentalContracts
   @Test fun misorderAdding() {
      buildCac7er("misorderAdding", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = buildCac7er("misorderAdding", testDir) {
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("a", "b"))
   }

   @ExperimentalContracts
   @Test fun removed() {
      buildCac7er("removed", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = buildCac7er("removed", testDir) {
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf(null, "b"))
   }

   @ExperimentalContracts
   @Test fun delegatee() {
      val delegatee = buildCac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = buildCac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("b", "a"))
   }

   @ExperimentalContracts
   @Test fun repositoryNameDeprecated() {
      val delegatee = buildCac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = buildCac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("a", "a"))
   }

   @ExperimentalContracts
   @Test fun addingDelegateeRepository() {
      var delegatee = buildCac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      buildCac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
      }

      delegatee = buildCac7er("delegatee", testDir) {
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = buildCac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("c", "a", "b"))
   }

   @ExperimentalContracts
   @Test fun removingDelegateeRepository() {
      var delegatee = buildCac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      buildCac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
      }

      delegatee = buildCac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = buildCac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("c", "a", null))
   }

   @ExperimentalContracts
   @Test fun complicated() {
      var delegatee = buildCac7er("delegatee", testDir) {
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("b", CacheOutput::writeInt, CacheInput::readInt)
      }

      buildCac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("d", CacheOutput::writeInt, CacheInput::readInt)
      }

      delegatee = buildCac7er("delegatee", testDir) {
         createRepository<Int, Int>("c", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("a", CacheOutput::writeInt, CacheInput::readInt)
      }

      val cac7er = buildCac7er("delegater", testDir) {
         delegatees += delegatee
         createRepository<Int, Int>("e", CacheOutput::writeInt, CacheInput::readInt)
         createRepository<Int, Int>("d", CacheOutput::writeInt, CacheInput::readInt)
      }

      assert(cac7er.repositories.map { it?.name } == listOf("d", "a", null, "e", "c"))
   }
}
