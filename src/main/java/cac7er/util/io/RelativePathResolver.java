package cac7er.util.io;

import java.io.File;

/**
 * faster than Kotlin stdlib while all arguments must be normalized
 */
public final class RelativePathResolver {
   private static final String UPSTREAM_STR = ".." + File.separatorChar;

   /**
    * toRelativePathFromParent("/parent/child", "/anotherDir/target")
    *       .equals("../anotherDir/target")
    *
    * toRelativePathFromParent("/parent/child", "/parent/target")
    *       .equals("target")
    */
   public static String toRelativePath(final String base, final String target) {
      final char[] baseChars   = base  .toCharArray();
      final char[] targetChars = target.toCharArray();

      int i = 0;

      while (i < baseChars.length) {
         final int baseI   = getNextSeparatorIndex(baseChars,   i);
         final int targetI = getNextSeparatorIndex(targetChars, i);

         if (!equals(baseChars,   i, baseI,
                     targetChars, i, targetI))
         {
            break;
         }

         if (baseI   == baseChars  .length) break;
         if (targetI == targetChars.length) break;

         i = baseI;
      }

      final int upstreamCount = getUpstreamCount(baseChars, i);

      final StringBuilder pathBuilder = new StringBuilder();

      for (int j = 0; j < upstreamCount; j++) {
         pathBuilder.append(UPSTREAM_STR);
      }

      pathBuilder.append(target.substring(i + 1));

      return pathBuilder.toString();
   }

   public static String resolve(final String base, final String relativePath) {
      final int upstreamCount = getUpstreamCount(relativePath);

      return getParentPath(base, upstreamCount + 1) +
            relativePath.substring(upstreamCount * UPSTREAM_STR.length());
   }

   private static int getNextSeparatorIndex(final char[] path,
                                            final int startIndex)
   {
      for (int i = startIndex + 1; i < path.length; i++) {
         if (path[i] == File.separatorChar) return i;
      }

      return path.length;
   }

   private static boolean equals(
         final char[] path1, final int start1, final int end1,
         final char[] path2, final int start2, final int end2)
   {
      if (end1 - start1 != end2 - start2) return false;

      for (int i = start1; i < end1; i++) {
         if (path1[i] != path2[i]) return false;
      }

      return true;
   }

   private static int getUpstreamCount(final char[] base,
                                       final int lastCommonPathIndex)
   {
      int count = 0;

      for (int i = lastCommonPathIndex + 1; i < base.length; i++) {
         if (base[i] == File.separatorChar) {
            count++;
         }
      }

      return count;
   }

   private static int getUpstreamCount(final String relativePath) {
      final char[] chars = relativePath.toCharArray();
      int count = 0;

      for (int i = 0; true; i += 3) {
         if (i + 2 < chars.length &&
               chars[i    ] == '.' &&
               chars[i + 1] == '.' &&
               chars[i + 2] == File.separatorChar)
         {
            count++;
         } else {
            return count;
         }
      }
   }

   /**
    * @return with '/' suffix
    */
   private static String getParentPath(final String basePath,
                                       int upstreamCount)
   {
      final char[] chars = basePath.toCharArray();

      int i = chars.length - 1;

      for (; i >= 0; i--) {
         if (chars[i] == File.separatorChar) {
            upstreamCount--;

            if (upstreamCount == 0) break;
         }
      }

      return basePath.substring(0, i + 1);
   }
}
