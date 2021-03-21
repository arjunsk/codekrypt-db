package com.arjunsk.db.components.fs.randomaccess;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class RandomAccessFileDriver {

  public static void main(String[] args) throws URISyntaxException {

    // This file will be read and written in /target/classes/input.txt folder and not in /src.
    URL resourceUrl = RandomAccessFileDriver.class.getClassLoader().getResource("input.txt");
    String filePath = Paths.get(resourceUrl.toURI()).toFile().getAbsolutePath();

    System.out.println(filePath);

    try {
      System.out.println(new String(readFromFile(filePath, 0, 18)));
      writeToFile(filePath, "I love my country and my people", 0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static byte[] readFromFile(String filePath, int position, int size) throws IOException {
    byte[] bytes;
    try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
      file.seek(position);
      bytes = new byte[size];
      file.read(bytes);
    }
    return bytes;
  }

  private static void writeToFile(String filePath, String data, int position) throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
      file.seek(position);
      file.write(data.getBytes(StandardCharsets.UTF_8));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    System.out.println("Written Successfully!");
  }
}
