package cac7er.serializer.ranges

import cac7er.serializer.*

fun CacheOutput.writeLongRange(value: LongRange) {
   writeLong(value.start)
   writeLong(value.endInclusive)
}

fun CacheInput.readLongRange(): LongRange = readLong()..readLong()
