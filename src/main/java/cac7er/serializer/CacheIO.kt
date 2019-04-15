package cac7er.serializer

import cac7er.*

import java.io.*
import java.util.*

class CacheOutput
      internal constructor(
            internal val output: DataOutputStream,
            internal val file: File,
            internal val cac7er: Cac7er
      )
{
   /** absolute file paths that are depended by this Cache. */
   internal val dependence = LinkedList<String>()
}

class CacheInput
      internal constructor(
            internal val input: DataInputStream,
            internal val cac7er: Cac7er
      )
