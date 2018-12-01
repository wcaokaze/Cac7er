[Cac7er](../../../index.md) / [cac7er](../../index.md) / [Cac7er](../index.md) / [Builder](index.md) / [delegatees](./delegatees.md)

# delegatees

`val delegatees: `[`MutableSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)`<`[`Cac7er`](../index.md)`>` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cac7er.kt#L154)

Normally, a instance of Cac7er cannot manage repositories in another
Cac7er.

``` kotlin
lateinit var statusRepository: Repository<Long, Status>
lateinit var userRepository:   Repository<Long, User>

val statusCac7er = Cac7er("status", File("statuses")) {
   statusRepository = createRepository("statuses",
         CacheOutput::writeStatus, CacheInput::readStatus)
}

val userCac7er = Cac7er("user", File("users")) {
   userRepository = createRepository("users",
         CacheOutput::writeUser, CacheInput::readUser)
}

data class Status(
      val id: Long,
      val content: String,
      val userCache: Cache<User>
)

data class User(
      val id: Long,
      val name: String
)
```

Here, `statusCac7er` can load a Cache of `Status`, but `statusCac7er`
*cannot* load a Cache of `User`. However `Status` has a Cache of `User`,
this causes a Exception.

Yes, this is a solution.

``` kotlin
val statusCac7er = Cac7er("status", File("statuses")) {
   delegatees += userCac7er

   statusRepository = createRepository("statuses",
         CacheOutput::writeStatus, CacheInput::readStatus)
}
```

**Since**
0.1.0

