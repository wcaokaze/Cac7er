package cac7er;

import java.io.*;

import java.util.LinkedList;
import java.util.List;

final class Cac7erMetadataFileService {
   static List<String> loadRepositoryNames(final File metadataFile)
         throws IOException
   {
      final List<String> repositoryNames = new LinkedList<String>();

      final DataInputStream inputStream = new DataInputStream(
            new BufferedInputStream(new FileInputStream(metadataFile)));

      try {
         final int repositoryCount = inputStream.readInt();

         for (int i = 0; i < repositoryCount; i++) {
            repositoryNames.add(inputStream.readUTF());
         }
      } finally {
         inputStream.close();
      }

      return repositoryNames;
   }

   static void writeRepositoryNames(final File metadataFile,
                                    final List<String> repositoryNames)
         throws IOException
   {
      final DataOutputStream outputStream = new DataOutputStream(
            new BufferedOutputStream(new FileOutputStream(metadataFile)));

      try {
         outputStream.writeInt(repositoryNames.size());

         for (final String name : repositoryNames) {
            outputStream.writeUTF(name);
         }
      } finally {
         outputStream.close();
      }
   }
}
