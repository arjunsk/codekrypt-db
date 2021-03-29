package com.arjunsk.db.components.thr;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomDriver {

  public static void main(String[] args) {
    // This method returns a pseudorandom long value.
    System.out.println("Random long value is: " + ThreadLocalRandom.current().nextLong());

    // Returns a pseudorandom, uniformly distributed long value between 0 and 2000
    System.out.println("Random long value is: " + ThreadLocalRandom.current().nextLong(2000));

    // Returns a pseudorandom, uniformly distributed long value between 10000 and 50000
    System.out.println("Random long value is: " + ThreadLocalRandom.current().nextLong(10000, 50000));
  }
}
