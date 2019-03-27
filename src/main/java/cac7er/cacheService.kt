package cac7er

import cac7er.serializer.*
import cac7er.util.io.*
import java.io.*

import kotlinx.coroutines.*

private val MAGIC_NUMBER = 0xcac7e000.toInt() or Cac7er.MAJOR_VERSION

/*
 *  0 MAGIC_NUMBER (4bytes)
 *  4 dependencePosition (4bytes)
 *  8 circulationRecordPosition (4bytes)
 * 12 content
 *  ? dependence
 *  ? circulationRecord
 */

internal fun <T> saveLazy(uniformizer: Uniformizer<T>) {
   uniformizer.repository.launch(writerCoroutineDispatcher + SupervisorJob()) {
      try {
         save(uniformizer)
         uniformizer.repository.cac7er.autoGc()
      } catch (e: Exception) {
         e.printStackTrace()
      }
   }
}

internal fun <T> incrementCirculationRecordLazy
      (uniformizer: Uniformizer<T>, time: Long, accessCount: Float)
{
   if (accessCount == 0.0f) return

   uniformizer.repository.launch(writerCoroutineDispatcher + SupervisorJob()) {
      try {
         uniformizer.circulationRecord.add(time, accessCount)
         saveCirculationRecord(uniformizer)
      } catch (e: Exception) {
         e.printStackTrace()
      }
   }
}

/**
 * writes the content of the specified cache into the file.
 *
 * NOTE:
 * This function is not recursive. If the content of the specified cache has
 * other [Cache]s, this function does NOT save it.
 *
 * @throws IOException
 */
private fun <T> save(uniformizer: Uniformizer<T>) {
   if (uniformizer.fileName.endsWith(".tmp")) {
      throw IllegalArgumentException(
            "The name of cache file must not end with \".tmp\"")
   }

   println("Cac7er: saving ${uniformizer.fileName}...")
   val startTime = System.currentTimeMillis()

   // mkdir was invoked on the constructor of Repository
   val repositoryDir = uniformizer.repository.dir

   val fileName = uniformizer.fileName

   val cacheFile = File(repositoryDir, fileName)
   val tmpFile = File(repositoryDir, "$fileName.tmp")

   var dependencePosition = 0
   var circulationRecordPosition = 0

   try {
      DataOutputStream(tmpFile.outputStream().buffered()).use {
         it.write(ByteArray(12))

         val output = CacheOutput(it, cacheFile, uniformizer.repository.cac7er)

         uniformizer.repository.serializer(output, uniformizer.content)

         dependencePosition = it.size()
         writeDependence(it, cacheFile.absolutePath, output.dependence)

         circulationRecordPosition = it.size()
         uniformizer.circulationRecord.writeTo(it)

         if (circulationRecordPosition > Int.MAX_VALUE) {
            throw IOException("The cache is too huge.")
         }
      }

      RandomAccessFile(tmpFile, "rw").use {
         it.writeInt(MAGIC_NUMBER)
         it.writeInt(dependencePosition)
         it.writeInt(circulationRecordPosition)
      }

      if (!tmpFile.renameTo(cacheFile)) {
         throw IOException("cannot rename file: ${cacheFile.absoluteFile}")
      }
   } finally {
      tmpFile.delete()
   }

   println("Cac7er: saving ${uniformizer.fileName} done! (${System.currentTimeMillis() - startTime}ms)")
}

/**
 * writes only circulationRecord of the specified cache or also the content
 * if it has not written yet.
 *
 * @throws IOException
 */
private fun saveCirculationRecord(uniformizer: Uniformizer<*>) {
   println("Cac7er: saving circulationRecord for ${uniformizer.fileName}...")
   val startTime = System.currentTimeMillis()

   val file = File(uniformizer.repository.dir, uniformizer.fileName)

   if (file.exists()) {
      RandomAccessFile(file, "rw").use {
         it.seek(8L)

         val circulationRecordPosition = it.readInt().toLong()
         it.seek(circulationRecordPosition)

         uniformizer.circulationRecord.writeTo(it)
      }
   } else {
      save(uniformizer)
   }

   println("Cac7er: saving circulationRecord for ${uniformizer.fileName} done! (${System.currentTimeMillis() - startTime}ms)")
}

internal suspend fun Uniformizer<*>.loadIfNecessary() {
   if (state != Uniformizer.State.EMPTY) return

   onStartToLoadContent()

   withContext(repository.coroutineContext + SupervisorJob()) {
      load(this@loadIfNecessary)
   }
}

internal fun Uniformizer<*>.loadBlockingIfNecessary() {
   if (state != Uniformizer.State.EMPTY) return

   onStartToLoadContent()
   load(this)
}

/**
 * loads a cache and set it into the uniformizer.
 */
private fun <T> load(uniformizer: Uniformizer<T>) {
   val file = File(uniformizer.repository.dir, uniformizer.fileName)

   if (!file.exists()) {
      uniformizer.setDeleted()
      return
   }

   DataInputStream(file.inputStream().buffered()).use {
      val magicNumber = it.readInt()

      if (magicNumber != MAGIC_NUMBER) {
         throw IOException("Cannot load a cache. " +
               "The file was not written by Cac7er")
      }

      val dependencePosition        = it.readInt().toLong()
      val circulationRecordPosition = it.readInt().toLong()

      val input = CacheInput(it, uniformizer.repository.cac7er)

      val content = uniformizer.repository.deserializer(input)

      it.skip(circulationRecordPosition - dependencePosition)

      val circulationRecord = try {
         CirculationRecord.readFrom(it)
      } catch (e: Exception) {
         CirculationRecord()
      }

      uniformizer.setLoadedContent(content, circulationRecord)
   }
}

internal data class Metadata(
      val dependence: List<String>,
      val circulationRecord: CirculationRecord
)

internal fun loadMetadata(file: File): Metadata {
   RandomAccessFile(file, "r").use {
      val magicNumber = it.readInt()

      if (magicNumber != MAGIC_NUMBER) {
         throw IOException("Cannot read the metadata. " +
               "The file was not written by Cac7er")
      }

      val dependencePosition = it.readInt().toLong()

      it.seek(dependencePosition)

      val dependence = loadDependence(it, file.absolutePath)
      val circulationRecord = CirculationRecord.readFrom(it)

      return cac7er.Metadata(dependence, circulationRecord)
   }
}

private fun writeDependence(randomAccessFile: DataOutputStream,
                            baseCacheFilePath: String,
                            dependence: List<String>)
{
   randomAccessFile.writeInt(dependence.size)

   for (filePath in dependence) {
      val relativePath = RelativePathResolver
            .toRelativePath(baseCacheFilePath, filePath)

      randomAccessFile.writeUTF(relativePath)
   }
}

private fun loadDependence(randomAccessFile: RandomAccessFile,
                           baseCacheFilePath: String): List<String>
{
   val size = randomAccessFile.readInt()

   return List(size) {
      val relativePath = randomAccessFile.readUTF()

      RelativePathResolver.resolve(baseCacheFilePath, relativePath)
   }
}
