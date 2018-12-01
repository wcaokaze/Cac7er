[Cac7er](../../../index.md) / [cac7er](../../index.md) / [Cac7er](../index.md) / [Builder](index.md) / [idealTotalFileSize](./ideal-total-file-size.md)

# idealTotalFileSize

`var idealTotalFileSize: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cac7er.kt#L156)

The total size of cache files to run [gc](../gc.md).

[WritableCache.save](../../-writable-cache/save.md) and [WritableRepository.save](../../-writable-repository/save.md) check the total
size and call [gc](../gc.md) if necessary.

