package com.arjunsk.db.components.lock.implicit;

import com.arjunsk.db.components.lock.implicit.support.SynchronizedCounter;

public class SynchronizedDriver {

  public static void main(String[] args) {
    new SynchronizedCounter().compute();
  }
}
