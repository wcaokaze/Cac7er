package cac7er.serializer.util

import cac7er.serializer.*
import java.util.Date

fun CacheOutput.writeDate(value: Date) {
   val time = value.time
   writeLong(time)
}

fun CacheInput.readDate(): Date {
   val time = readLong()
   return Date(time)
}
