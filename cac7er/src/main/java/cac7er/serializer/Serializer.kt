package cac7er.serializer

typealias Serializer<T> = CacheOutput.(value: T) -> Unit
typealias Deserializer<T> = CacheInput.() -> T
