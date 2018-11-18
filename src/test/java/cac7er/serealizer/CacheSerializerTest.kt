package cac7er.serealizer

import cac7er.*

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*

import java.io.*
import cac7er.serializer.*
import kotlinx.coroutines.*

import kotlin.contracts.*

@RunWith(JUnit4::class)
class CacheSerializerTest {
   private val testDir = File("testOutput")

   @After fun deleteDir() {
      // wait until all Jobs for saving are done.
      Thread.sleep(50L)

      testDir.deleteRecursively()
   }

   @ExperimentalContracts
   @Test fun cache() {
      val repo: WritableRepository<Int, String>

      val cac7er = buildCac7er("cache", testDir) {
         repo = createRepository("repo",
               CacheOutput::writeString, CacheInput::readString)
      }

      val cache = repo.save(0, "wcaokaze")

      with (cac7er.getCacheOutput("anotherRepo")) {
         writeCache(cache)
      }

      val readCache = with (cac7er.getCacheInput("anotherRepo")) {
         readCache<String>()
      }

      assert(readCache.get() == cache.get())

      cache.save("ezakoacw")

      assert(readCache.get() == cache.get())
   }

   @ExperimentalContracts
   @Test fun lazyCache() = runBlocking {
      val repo: WritableRepository<Int, String>

      val cac7er = buildCac7er("lazyCache", testDir) {
         repo = createRepository("repo",
               CacheOutput::writeString, CacheInput::readString)
      }

      val cache = repo.save(0, "wcaokaze").toLazyCache()

      with (cac7er.getCacheOutput("anotherRepo")) {
         writeLazyCache(cache)
      }

      val readCache = with (cac7er.getCacheInput("anotherRepo")) {
         readLazyCache<String>()
      }

      assert(readCache.get() == cache.get())

      cache.save("ezakoacw")

      assert(readCache.get() == cache.get())
   }

   @ExperimentalContracts
   @Test fun weakCache() {
      val repo: WritableRepository<Int, String>

      val cac7er = buildCac7er("weakCache", testDir) {
         repo = createRepository("repo",
               CacheOutput::writeString, CacheInput::readString)
      }

      val cache = repo.save(0, "wcaokaze").toWeakCache()

      with (cac7er.getCacheOutput("anotherRepo")) {
         writeWeakCache(cache)
      }

      val readCache = with (cac7er.getCacheInput("anotherRepo")) {
         readWeakCache<String>()
      }

      assert(readCache.get() == cache.get())

      cache.save("ezakoacw")

      assert(readCache.get() == cache.get())
   }

   @ExperimentalContracts
   @Test fun delegatee() {
      val repo: WritableRepository<Int, String>

      val cac7erA = buildCac7er("delegateeA", File(testDir, "a")) {
         repo = createRepository("repo",
               CacheOutput::writeString, CacheInput::readString)
      }

      val cac7erB = buildCac7er("delegateeB", File(testDir, "b")) {
         delegatees += cac7erA
      }

      val cache = repo.save(0, "wcaokaze")

      with (cac7erB.getCacheOutput("anotherRepo")) {
         writeCache(cache)
      }

      val readCache = with (cac7erB.getCacheInput("anotherRepo")) {
         readCache<String>()
      }

      assert(readCache.get() == cache.get())

      cache.save("ezakoacw")

      assert(readCache.get() == cache.get())
   }

   @ExperimentalContracts
   @Test fun imitate2wiqua() {
      data class TwitterAccount(val id: String, val username: String)
      data class TwitterStatus(val id: String, val accountCache: Cache<TwitterAccount>)

      data class MastodonAccount(val id: String, val username: String)
      data class MastodonStatus(val id: String, val accountCache: Cache<MastodonAccount>)

      data class TwitterStatusPage(val id: String, val statusCache: Cache<TwitterStatus>)
      data class MastodonStatusPage(val id: String, val statusCache: Cache<MastodonStatus>)

      fun CacheOutput.writeTwitterAccount(value: TwitterAccount) {
         writeString(value.id)
         writeString(value.username)
      }

      fun CacheInput.readTwitterAccount() = TwitterAccount(
            id       = readString(),
            username = readString()
      )

      fun CacheOutput.writeTwitterStatus(value: TwitterStatus) {
         writeString(value.id)
         writeCache(value.accountCache)
      }

      fun CacheInput.readTwitterStatus() = TwitterStatus(
            id           = readString(),
            accountCache = readCache()
      )

      fun CacheOutput.writeMastodonAccount(value: MastodonAccount) {
         writeString(value.id)
         writeString(value.username)
      }

      fun CacheInput.readMastodonAccount() = MastodonAccount(
            id       = readString(),
            username = readString()
      )

      fun CacheOutput.writeMastodonStatus(value: MastodonStatus) {
         writeString(value.id)
         writeCache(value.accountCache)
      }

      fun CacheInput.readMastodonStatus() = MastodonStatus(
            id           = readString(),
            accountCache = readCache()
      )

      fun CacheOutput.writeTwitterStatusPage(value: TwitterStatusPage) {
         writeString(value.id)
         writeCache(value.statusCache)
      }

      fun CacheInput.readTwitterStatusPage() = TwitterStatusPage(
            id          = readString(),
            statusCache = readCache()
      )

      fun CacheOutput.writeMastodonStatusPage(value: MastodonStatusPage) {
         writeString(value.id)
         writeCache(value.statusCache)
      }

      fun CacheInput.readMastodonStatusPage() = MastodonStatusPage(
            id          = readString(),
            statusCache = readCache()
      )

      val twitterAccountRepo: WritableRepository<String, TwitterAccount>
      val twitterStatusRepo:  WritableRepository<String, TwitterStatus>

      val twitterCac7er = buildCac7er("twitter", File(testDir, "twitter")) {
         twitterAccountRepo = createRepository("account",
               CacheOutput::writeTwitterAccount, CacheInput::readTwitterAccount)

         twitterStatusRepo = createRepository("status",
               CacheOutput::writeTwitterStatus, CacheInput::readTwitterStatus)
      }

      val mastodonAccountRepo: WritableRepository<String, MastodonAccount>
      val mastodonStatusRepo:  WritableRepository<String, MastodonStatus>

      val mastodonCac7er = buildCac7er("mastodon", File(testDir, "mastodon")) {
         mastodonAccountRepo = createRepository("account",
               CacheOutput::writeMastodonAccount, CacheInput::readMastodonAccount)

         mastodonStatusRepo = createRepository("status",
               CacheOutput::writeMastodonStatus, CacheInput::readMastodonStatus)
      }

      val twitterAccount = TwitterAccount("1234", "wcaokaze")
      val twitterStatus = TwitterStatus("0", twitterAccountRepo.save(twitterAccount.id, twitterAccount))
      val twitterStatusPage = TwitterStatusPage(twitterStatus.id, twitterStatusRepo.save(twitterStatus.id, twitterStatus))

      val mastodonAccount = MastodonAccount("2345", "wcaokaze")
      val mastodonStatus = MastodonStatus("3", mastodonAccountRepo.save(mastodonAccount.id, mastodonAccount))
      val mastodonStatusPage = MastodonStatusPage(mastodonStatus.id, mastodonStatusRepo.save(mastodonStatus.id, mastodonStatus))

      val pageCac7er = buildCac7er("page", File(testDir, "page")) {
         delegatees += twitterCac7er
         delegatees += mastodonCac7er
      }

      with (pageCac7er.getCacheOutput("twitterStatusPage")) {
         writeTwitterStatusPage(twitterStatusPage)
      }

      with (pageCac7er.getCacheInput("twitterStatusPage")) {
         val readPage = readTwitterStatusPage()

         assert(twitterStatusPage == readPage)

         assert(twitterStatusPage.statusCache.get().accountCache.get()
                     === readPage.statusCache.get().accountCache.get())
      }

      with (pageCac7er.getCacheOutput("mastodonStatusPage")) {
         writeMastodonStatusPage(mastodonStatusPage)
      }

      with (pageCac7er.getCacheInput("mastodonStatusPage")) {
         val readPage = readMastodonStatusPage()

         assert(mastodonStatusPage == readPage)

         assert(mastodonStatusPage.statusCache.get().accountCache.get()
                      === readPage.statusCache.get().accountCache.get())
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
