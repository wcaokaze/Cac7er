[Cac7er](../index.md) / [cac7er](index.md) / [buildCac7er](./build-cac7er.md)

# buildCac7er

`inline fun buildCac7er(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, dir: `[`File`](http://docs.oracle.com/javase/6/docs/api/java/io/File.html)`, builderAction: `[`Builder`](-cac7er/-builder/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Cac7er`](-cac7er/index.md) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cac7er.kt#L40)

builds a new instance.

Initialize repositories in the lambda.

``` kotlin
lateinit var userRepository: Repository<Long, User>
   private set

lateinit var statusRepository: Repository<Long, Status>
   private set

val cac7er = Cac7er("twitterCaches", File("path/to/directory")) {
   userRepository = createRepository("users",
         CacheOutput::writeUser, CacheInput::readUser)

   statusRepository = createRepository("statuses",
         CacheOutput::writeStatus, CacheInput::readStatus)
}
```

see [README](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/README.md)
for more information.

This can take time. If your Cac7er has many repositories, consider
instantiation with [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html).

