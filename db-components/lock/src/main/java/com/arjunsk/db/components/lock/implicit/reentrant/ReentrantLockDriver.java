package com.arjunsk.db.components.lock.implicit.reentrant;

import com.arjunsk.db.components.lock.implicit.reentrant.support.ReentrantLockSleep;

/**
 * @see <a
 *     href="https://winterbe.com/posts/2015/04/30/java8-concurrency-tutorial-synchronized-locks-examples/">Lock</a>
 */
public class ReentrantLockDriver {

  public static void main(String[] args) {
    new ReentrantLockSleep().compute();
  }
}
