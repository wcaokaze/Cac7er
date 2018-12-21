[Cac7er](../../index.md) / [cac7er](../index.md) / [Cac7er](./index.md)

# Cac7er

`class Cac7er : `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/Cac7er.kt#L50)

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `class Builder` |

### Properties

| Name | Summary |
|---|---|
| [coroutineContext](coroutine-context.md) | `val coroutineContext: `[`CoroutineContext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html) |
| [dir](dir.md) | `val dir: `[`File`](http://docs.oracle.com/javase/6/docs/api/java/io/File.html) |
| [name](name.md) | `val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [gc](gc.md) | `fun gc(idealTotalFileSize: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Job`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)<br>runs the garbage collector. GC deletes some files whose `importance` are low. |

### Companion Object Properties

| Name | Summary |
|---|---|
| [MAJOR_VERSION](-m-a-j-o-r_-v-e-r-s-i-o-n.md) | `val MAJOR_VERSION: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [MINOR_VERSION](-m-i-n-o-r_-v-e-r-s-i-o-n.md) | `val MINOR_VERSION: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [REVISION](-r-e-v-i-s-i-o-n.md) | `val REVISION: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [VERSION](-v-e-r-s-i-o-n.md) | `val VERSION: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Extension Functions

| Name | Summary |
|---|---|
| [Projector](../../cac7er.projector/kotlinx.coroutines.-coroutine-scope/-projector.md) | `fun <T> `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.Projector(onStartToLoadLazyCache: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onFailedToLoadLazyCache: (`[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onSetDeletedCache: (`[`WeakCache`](../-weak-cache/index.md)`<`[`T`](../../cac7er.projector/kotlinx.coroutines.-coroutine-scope/-projector.md#T)`>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onCacheUpdate: (`[`T`](../../cac7er.projector/kotlinx.coroutines.-coroutine-scope/-projector.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Projector`](../../cac7er.projector/-projector/index.md)`<`[`T`](../../cac7er.projector/kotlinx.coroutines.-coroutine-scope/-projector.md#T)`>``fun <T> `[`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)`.Projector(onNull: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, onContent: (`[`T`](../../cac7er.projector/kotlinx.coroutines.-coroutine-scope/-projector.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Projector`](../../cac7er.projector/-projector/index.md)`<`[`T`](../../cac7er.projector/kotlinx.coroutines.-coroutine-scope/-projector.md#T)`?>`<br>In most cases, onStartToLoadLazyCache and onFailedToLoadLazyCache and onSetDeletedCache are same function. |
