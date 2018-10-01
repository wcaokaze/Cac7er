package cac7er.serializer.util

import cac7er.serializer.*
import java.util.*

fun CacheOutput.writeDate(value: Date) {
   val time = value.time
   writeLong(time)
}

fun CacheInput.readDate(): Date {
   val time = readLong()
   return Date(time)
}

fun CacheOutput.writeDateWithTimeZone(
      value: Date,
      timeZone: TimeZone = TimeZone.getDefault())
{
   val utcTime = value.time - timeZone.rawOffset
   writeLong(utcTime)
}

fun CacheInput.readDateWithTimeZone(
      value: Date,
      timeZone: TimeZone = TimeZone.getDefault()): Date
{
   val time = readLong()
   return Date(time + timeZone.rawOffset)
}
