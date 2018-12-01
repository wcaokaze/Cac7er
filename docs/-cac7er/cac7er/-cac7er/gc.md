[Cac7er](../../index.md) / [cac7er](../index.md) / [Cac7er](index.md) / [gc](./gc.md)

# gc

`@Synchronized fun gc(idealTotalFileSize: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cac7er.kt#L256)

runs the garbage collector. GC deletes some files whose `importance` are
low.

It is guaranteed that cache files that are depended by any other cache
which is not deleted are also not deleted. For example:

``` kotlin
class A
class B(val aCache: Cache<A>)

// ...

val aCache = aRepository.save("a", A())
val bCache = bRepository.save("b", B(aCache))

// ...

cac7er.gc(100L)
```

Then, if `b` is not deleted, `a` is never deleted. If `a` is deleted, `b`
is also deleted.

Note that Cac7er can delete files consider about its delegaters. In other
words, when your Cac7er delegates another Cac7er, the delegatee can delete
files even if your Cac7er depends on them.

### Parameters

`idealTotalFileSize` - The ideal total size of files.

### Exceptions

`IOException` - when any file could not be loaded

**See Also**

[Cache.get](../-cache/get.md)

[WeakCache](../-weak-cache/index.md)

**Since**
1.0.0

