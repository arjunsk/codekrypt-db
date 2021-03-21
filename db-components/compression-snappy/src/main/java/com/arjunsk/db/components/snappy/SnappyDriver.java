package com.arjunsk.db.components.snappy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.xerial.snappy.Snappy;

public class SnappyDriver {

  public static void main(String[] args) throws IOException {

    String input =
        "Hello snappy-java! Snappy-java is a JNI-based wrapper of "
            + "Snappy, a fast compresser/decompresser.";

    byte[] compressed = Snappy.compress(input.getBytes(StandardCharsets.UTF_8));
    byte[] uncompressed = Snappy.uncompress(compressed);

    String result = new String(uncompressed, StandardCharsets.UTF_8);
    System.out.println(result);
  }
}
