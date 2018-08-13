package cac7er.serializer

import cac7er.*

/**
 * using reflection internal. The specialized serializer for E may make it faster.
 */
fun <E : Enum<E>> CacheOutput.writeEnum(value: Enum<E>) {
   val name = value.name
   writeString(name)
}

/**
 * using reflection internal. The specialized serializer for E may make it faster.
 */
inline fun <reified E : Enum<E>> CacheInput.readEnum(): E {
   val name = readString()
   return enumValueOf(name)
}
