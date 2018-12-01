[Cac7er](../../index.md) / [cac7er.util.io](../index.md) / [RelativePathResolver](./index.md)

# RelativePathResolver

`class RelativePathResolver` [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/util/io/RelativePathResolver.java#L8)

faster than Kotlin stdlib while all arguments must be normalized

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `RelativePathResolver()`<br>faster than Kotlin stdlib while all arguments must be normalized |

### Functions

| Name | Summary |
|---|---|
| [resolve](resolve.md) | `static fun resolve(base: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, relativePath: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toRelativePath](to-relative-path.md) | `static fun toRelativePath(base: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, target: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>toRelativePathFromParent("/parent/child", "/anotherDir/target") .equals("../anotherDir/target") toRelativePathFromParent("/parent/child", "/parent/target") .equals("target") |
