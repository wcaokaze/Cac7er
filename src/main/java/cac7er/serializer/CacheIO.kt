package cac7er.serializer

import java.io.*
import java.util.*

class CacheOutput internal constructor(internal val file: RandomAccessFile) {
   /** absolute file paths that are depended by this Cache. */
   internal val dependence = LinkedList<String>()
}

class CacheInput internal constructor(internal val file: RandomAccessFile)
