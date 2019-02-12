package cac7er

import cac7er.serializer.*
import cac7er.util.io.*
import java.io.*

private val MAGIC_NUMBER = 0xcac7e000.toInt() or Cac7er.MAJOR_VERSION

/*
 *  0 MAGIC_NUMBER (4bytes)
 *  4 dependencePosition (4bytes)
 *  8 circulationRecordPosition (4bytes)
 * 12 content
 *  ? dependence
 *  ? circulationRecord
 */

/**
 * writes the content of the specified cache into the file.
 *
 * NOTE:
 * This function is not recursive. If the content of the specified cache has
 * other [Cache]s, this function does NOT save it.
 *
 * @throws IOException
 */
internal fun <T> save(uniformizer: Uniformizer<T>) {
   if (uniformizer.fileName.endsWith(".tmp")) {
      throw IllegalArgumentException(
            "The name of cache file must not end with \".tmp\"")
   }

   // mkdir was invoked on the constructor of Repository
   val repositoryDir = uniformizer.repository.dir

   val fileName = uniformizer.fileName

   val cacheFile = File(repositoryDir, fileName)
   val tmpFile = File(repositoryDir, "$fileName.tmp")

   RandomAccessFile(tmpFile, "rw").use {
      it.writeInt(MAGIC_NUMBER)
      it.seek(12L)

      val output = CacheOutput(it, cacheFile, uniformizer.repository.cac7er)

      uniformizer.repository.serializer(output, uniformizer.content)

      val dependencePosition = it.filePointer
      writeDependence(it, cacheFile.absolutePath, output.dependence)

      val circulationRecordPosition = it.filePointer
      uniformizer.circulationRecord.writeTo(it)

      if (circulationRecordPosition > Int.MAX_VALUE) {
         throw IOException("The cache is too huge.")
      }

      it.seek(4L)
      it.writeInt(dependencePosition.toInt())
      it.writeInt(circulationRecordPosition.toInt())
   }

   if (!tmpFile.renameTo(cacheFile)) {
      throw IOException("cannot rename file: ${cacheFile.absoluteFile}")
   }
}

/**
 * writes only circulationRecord of the specified cache or also the content
 * if it has not written yet.
 *
 * @throws IOException
 */
internal fun saveCirculationRecord(uniformizer: Uniformizer<*>) {
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
}

/**
 * loads a cache and set it into the uniformizer.
 */
internal fun <T> load(uniformizer: Uniformizer<T>) {
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

private fun writeDependence(randomAccessFile: RandomAccessFile,
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
