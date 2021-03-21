package com.arjunsk.db.components.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLockExample {

  public static void main(String[] args) throws IOException, URISyntaxException {

    String input = "Demo text to be written in locked mode.";
    System.out.println("Input string to the test file is: " + input);

    ByteBuffer buf = ByteBuffer.wrap(input.getBytes());

    // The input.txt is in target/classes/input.txt and not in resources/input.txt
    URL resourceUrl = FileLockExample.class.getClassLoader().getResource("input.txt");
    String filePath = Paths.get(resourceUrl.toURI()).toFile().getAbsolutePath();
    Path path = Paths.get(filePath);

    try (FileChannel channel =
        FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {

      channel.position(channel.size()); // position of a cursor at the end of file

      FileLock lock = channel.lock();
      System.out.println("The Lock is shared: " + lock.isShared());

      channel.write(buf);
    } finally {
      System.out.println(
          "Content Writing is complete. Therefore close the channel and release the lock.");

      print(filePath);
    }
  }

  public static void print(String path) throws IOException {
    try (FileReader filereader = new FileReader(path);
        BufferedReader bufferedreader = new BufferedReader(filereader)) {
      String tr = bufferedreader.readLine();

      System.out.println("The Content of input.txt file is: ");

      while (tr != null) {
        System.out.print(tr);
        tr = bufferedreader.readLine();
      }
    }
  }
}
