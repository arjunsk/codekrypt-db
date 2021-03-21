package com.arjunsk.db.components.lock.explicit.reentrant.support;

import static com.arjunsk.db.components.utils.ConcurrentUtils.sleep;
import static com.arjunsk.db.components.utils.ConcurrentUtils.stop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSleep {

  private final ExecutorService executor;
  private final ReentrantLock reentrantLock;

  public ReentrantLockSleep() {
    this.executor = Executors.newFixedThreadPool(2);
    this.reentrantLock = new ReentrantLock();
  }

  public void compute() {

    // Job 1
    executor.submit(
        () -> {
          reentrantLock.lock();
          try {
            sleep(1);
          } finally {
            reentrantLock.unlock();
          }
        });

    // Job 2
    executor.submit(
        () -> {
          System.out.println("Locked: " + reentrantLock.isLocked());
          System.out.println("Held by me: " + reentrantLock.isHeldByCurrentThread());
          boolean locked = reentrantLock.tryLock();
          System.out.println("Lock acquired: " + locked);
        });

    stop(executor);
  }
}
