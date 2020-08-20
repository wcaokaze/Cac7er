package cac7er.serializer.ranges

import cac7er.serializer.*

fun CacheOutput.writeIntRange(value: IntRange) {
   writeInt(value.start)
   writeInt(value.endInclusive)
}

fun CacheInput.readIntRange(): IntRange = readInt()..readInt()
