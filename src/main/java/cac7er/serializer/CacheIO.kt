package cac7er.serializer

import cac7er.*

import java.io.*
import java.util.*

class CacheOutput
      internal constructor(
            internal val file: RandomAccessFile,
            internal val repository: RepositoryImpl<*, *>
      )
{
   /** absolute file paths that are depended by this Cache. */
   internal val dependence = LinkedList<String>()
}

class CacheInput internal constructor(internal val file: RandomAccessFile)
