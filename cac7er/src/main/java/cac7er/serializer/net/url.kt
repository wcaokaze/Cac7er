package cac7er.serializer.net

import cac7er.serializer.*
import java.net.URL

fun CacheOutput.writeUrl(value: URL) {
   val str = value.toExternalForm()
   writeString(str)
}

fun CacheInput.readUrl(): URL {
   val str = readString()
   return URL(str)
}
