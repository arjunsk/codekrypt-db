package com.arjunsk.db.components.lock.explicit;

import com.arjunsk.db.components.lock.explicit.support.SynchronizedCounter;

public class SynchronizedDriver {

  public static void main(String[] args) {
    new SynchronizedCounter().compute();
  }
}
