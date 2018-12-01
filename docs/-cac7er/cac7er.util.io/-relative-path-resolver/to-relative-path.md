[Cac7er](../../index.md) / [cac7er.util.io](../index.md) / [RelativePathResolver](index.md) / [toRelativePath](./to-relative-path.md)

# toRelativePath

`static fun toRelativePath(base: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, target: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) [(source)](http://2wiqua.wcaokaze.com/gitbucket/wcaokaze/Cac7er/blob/master/src/main/java/cac7er/util/io/RelativePathResolver.java#L18)

toRelativePathFromParent("/parent/child", "/anotherDir/target") .equals("../anotherDir/target") toRelativePathFromParent("/parent/child", "/parent/target") .equals("target")

