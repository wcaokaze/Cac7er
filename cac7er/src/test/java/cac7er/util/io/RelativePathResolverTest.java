package cac7er.util.io;

import org.junit.*;
import static org.junit.Assert.*;

public final class RelativePathResolverTest {
  @Test public void sibling() {
    assertEquals(RelativePathResolver
        .toRelativePath("/parent/child", "/parent/another"),
        "another");

    assertEquals(RelativePathResolver
        .resolve("/parent/child", "another"),
        "/parent/another");
  }

  @Test public void parent() {
    assertEquals(RelativePathResolver
        .toRelativePath("/parent/child", "/another"),
        "../another");

    assertEquals(RelativePathResolver
        .resolve("/parent/child", "../another"),
        "/another");
  }

  @Test public void parentItself() {
    assertEquals(RelativePathResolver
        .toRelativePath("/parent/child", "/parent"),
        "../parent");

    assertEquals(RelativePathResolver
        .resolve("/parent/child", "../parent"),
        "/parent");
  }

  @Test public void identity() {
    assertEquals(RelativePathResolver
        .toRelativePath("/parent/child", "/parent/child"),
        "child");

    assertEquals(RelativePathResolver
        .resolve("/parent/child", "child"),
        "/parent/child");
  }

  @Test public void child() {
    assertEquals(RelativePathResolver
        .toRelativePath("/parent", "/parent/child"),
        "parent/child");

    assertEquals(RelativePathResolver
        .resolve("/parent", "parent/child"),
        "/parent/child");
  }

  @Test public void remote() {
    assertEquals(RelativePathResolver
        .toRelativePath("/parent/child", "/another/target"),
        "../another/target");

    assertEquals(RelativePathResolver
        .resolve("/parent/child", "../another/target"),
        "/another/target");
  }
}
