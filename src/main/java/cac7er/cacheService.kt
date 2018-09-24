package cac7er

import cac7er.serializer.*
import cac7er.util.io.*
import java.io.*

private const val MAGIC_NUMBER = 0xcac7e000.toInt() or Cac7er.MAJOR_VERSION

/*
 * 0 MAGIC_NUMBER (4bytes)
 * 4 dependencePosition (2bytes)
 * 6 circulationRecordPosition (2bytes)
 * 8 content
 * ? dependence
 * ? circulationRecord
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
      it.seek(8L)

      val output = CacheOutput(it, cacheFile, uniformizer.repository.cac7er)

      uniformizer.repository.serializer(output, uniformizer.content)

      val dependencePosition = it.filePointer
      writeDependence(it, cacheFile.absolutePath, output.dependence)

      val circulationRecordPosition = it.filePointer
      uniformizer.circulationRecord.writeTo(it)

      if (circulationRecordPosition > 65535) {
         throw IOException("The cache is too huge.")
      }

      it.seek(4L)
      it.writeShort(dependencePosition.toInt())
      it.writeShort(circulationRecordPosition.toInt())
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
         it.seek(6L)

         val circulationRecordPosition = it.readUnsignedShort().toLong()
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

   RandomAccessFile(file, "r").use {
      val magicNumber = it.readInt()

      if (magicNumber != MAGIC_NUMBER) {
         throw IOException("Cannot load a cache. " +
               "The file was not written by Cac7er")
      }

      @Suppress("UNUSED_VARIABLE")
      val dependencePosition        = it.readUnsignedShort().toLong()
      val circulationRecordPosition = it.readUnsignedShort().toLong()

      val input = CacheInput(it)

      val content = uniformizer.repository.deserializer(input)

      it.seek(circulationRecordPosition)

      val circulationRecord = try {
         CirculationRecord.readFrom(it)
      } catch (e: Exception) {
         CirculationRecord()
      }

      uniformizer.setLoadedContent(content, circulationRecord)
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

      randomAccessFile.writeChars(relativePath)
   }
}
