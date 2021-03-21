package com.arjunsk.db.components.fs.randomaccess;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class RandomAccessFileDriver {

  public static void main(String[] args) throws URISyntaxException {

    URL resourceUrl = RandomAccessFileDriver.class.getClassLoader().getResource("input.txt");
    File resourceFile = Paths.get(resourceUrl.toURI()).toFile();

    String absolutePath = resourceFile.getAbsolutePath();
    System.out.println(absolutePath);

    try {
      System.out.println(new String(readFromFile(resourceFile, 0, 18)));
      writeToFile(resourceFile, "I love my country and my people", 0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static byte[] readFromFile(File resourceFile, int position, int size) throws IOException {
    byte[] bytes;
    try (RandomAccessFile file = new RandomAccessFile(resourceFile, "r")) {
      file.seek(position);
      bytes = new byte[size];
      file.read(bytes);
    }
    return bytes;
  }

  private static void writeToFile(File resourceFile, String data, int position) throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(resourceFile, "rw")) {
      file.seek(position);
      file.write(data.getBytes(StandardCharsets.UTF_8));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
