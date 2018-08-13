package cac7er

import java.io.*

typealias Serializer<T> = T.(output: OutputStream) -> Unit
typealias Deserializer<T> = (input: InputStream) -> T
