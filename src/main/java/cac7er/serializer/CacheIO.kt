package cac7er.serializer

import java.io.*
import java.util.*

class CacheOutput internal constructor(
      /** The file which this Cache is being written into. */
      internal val baseCacheFile: File,

      internal val stream: OutputStream,

      /** relative file paths that are depended by this Cache. */
      internal val dependence: LinkedList<String>
)

class CacheInput internal constructor(
      /** The file which this Cache is being read from. */
      internal val baseCacheFile: File,

      internal val stream: InputStream
)
