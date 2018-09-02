
Cac7er
================================================================================
[Cac2er](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac2er) works too slowly.
Cac2er's encapsulation is too poor. Cac2er has many similar functions.

Cac7er decided to stop using reflection (including Serializable) even though we
must write a great number of functions to serialize. Cac7er decided to make core
functions private even though its extendability becomes poor. Cac7er decided to
rename functions as readable even though them names becomes longer.


Introduction
--------------------------------------------------------------------------------
Cac7er is a library for 2wiqua. It provides simple APIs to cache instances into
files. Cac7er is written in [Kotlin](http://kotlinlang.org) and suited for
Kotlin.


Requirements
--------------------------------------------------------------------------------
- Kotlin
- Writable files
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)


Tutorial
--------------------------------------------------------------------------------

### To save instances

```kotlin
lateinit var userRepository: Repository<Long, User>
   private set

val cac7er = Cac7er(File("dir/for/caches")) {
   userRepository = createRepository<Long, User>(
         name = "users",
         serializer   = CacheOutput::writeUser,
         deserializer = CacheInput ::readUser)
}

fun someFunction() {
   val userCache: Cache<User> = userRepository.save(user.id, user)
}
```

Yes it's ugly. Cac7er must know all repositories before the constructor
finishing. So Cac7er cannot avoid using such ugly DSL. Kotlin 1.3 introducing
Contracts, `lateinit var` becomes `val`. And of course types are inferred.  
Probably it becomes the follow:
```kotlin
val userRepository: Repository<Long, User>

val cac7er = Cac7er(File("dir/for/caches")) {
   userRepository = createRepository("users",
         CacheOutput::writeUser, CacheInput::readUser)
}

fun someFunction() {
   val userCache = userRepository.save(user.id, user)
}
```


Cac7er will write files like the follow directory structure. (Cac7er-meta is a
file which Cac7er writes metadata in.)
```
    dir/for/caches
     ├ Cac7er-meta
     ├ users
     │  ├ 69852
     │  ├ 69853
     │  ├ 69855
     │  └ 69856
     └ statuses
        ├ 8942140
        └ 8942152
```


### To load instances

```kotlin
launch {
   val userCache: Cache<User> = userRepository.load(id)
   val user: User = userCache.get()
}
```

`load` is a suspend function.


### Serializer

```kotlin
fun CacheInput.readUser(value: User) = User(
      id   = readLong(),
      name = readString(),
      // ...
)

fun CacheOutput.writeUser(value: User) {
   writeLong  (value.id)
   writeString(value.name)
   // ...
}
```

Cac7er never uses reflection. We must write serializer for every class in return
for performance. But it's not so troublesome, is it?


### Efficiency

In fact, we have more complex construction.

![](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/raw/master/timeline.svg)

```kotlin
data class Status(
      val id: Long,
      val user: User,
      val text: String,
      val date: Date,
      // ...
)

data class User(
      val id: Long,
      val name: String,
      // ...
)
```

![](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/raw/master/badInstances.svg)

There is a unique instance for `User` "Alex". However, as serialized,

* status/13554

    ```javascript
    {
      id: 13554,
      user: {
        id: 1,
        name: "Alex"
      },
      text: "Sushi is my favorite food",
      date: 1504398013
    }
    ```

* status/13555

    ```javascript
    {
      id: 13555,
      user: {
        id: 1,
        name: "Alex"
      },
      text: "🍣",
      date: 1504398165
    }
    ```

"Alex" is written twice! Moreover, de-serialized instances are twice too.

![](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/raw/master/badInstances2.svg)

To avoid this, let `Status` have [Cache][].

```kotlin
class Status(
      val id: Long,
      val userCache: Cache<User>,
      val text: String,
      val date: Date,
      // ...
)
```

![](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/raw/master/goodInstances.svg)

`CacheOutput.writeCache` writes only the repository name and the file name.

* status/13554

    ```javascript
    {
      id: 13554,
      userCache: {
         repository: "user",
         fileName: "1"
      },
      text: "Sushi is my favorite food",
      date: 1504398013
    }
    ```

* status/13555

    ```javascript
    {
      id: 13555,
      userCache: {
         repository: "user",
         fileName: "1"
      },
      text: "🍣",
      date: 1504398165
    }
    ```

* user/1

    ```javascript
    {
      id: 1,
      name: "Alex"
    }
    ```

`User` "Alex" is unique while de-serialized [Cache][]s are twice.

![](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/raw/master/goodInstances2.svg)


### GC

Normally, Cac7er is used to reduce Network I/O. It is impossible to cache all of
the data from unlimited resources. Cac7er has a mechanism to remove some files
that are not frequently used.

Here is a sample code to keep 2 MiB of caches.
```kotlin
cac7er.gc(idealTotalFileSize = 2L shl 20)
```


### accessCount

On GC, Cac7er must consider how caches are important for your application.
To let Cac7er know that, pass `accessCount` to `Cache.get`.
```kotlin
val user = userCache.get(accessCount = 1.0f)
```

This is all you have to do.

