package com.arjunsk.db.components.semaphores;

import com.arjunsk.db.components.semaphores.support.SemaphoreReadLimit;

public class SemaphoreDriver {
  public static void main(String[] args) {
    new SemaphoreReadLimit().test();
  }
}
