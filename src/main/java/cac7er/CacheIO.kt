package cac7er

import java.io.*

class CacheOutput
      internal constructor(internal val stream: OutputStream)

class CacheInput
      internal constructor(internal val stream: InputStream)

typealias Serializer<T> = CacheOutput.(value: T) -> Unit
typealias Deserializer<T> = CacheInput.() -> T
