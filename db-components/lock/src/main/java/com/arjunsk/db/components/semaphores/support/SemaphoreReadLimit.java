package com.arjunsk.db.components.semaphores.support;

import static com.arjunsk.db.components.utils.ConcurrentUtils.sleep;
import static com.arjunsk.db.components.utils.ConcurrentUtils.stop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreReadLimit {

  private final ExecutorService executor;
  private final Semaphore semaphore;

  public SemaphoreReadLimit() {
    executor = Executors.newFixedThreadPool(10);
    semaphore = new Semaphore(5);
  }

  public void test() {

    Runnable longRunningTask =
        () -> {
          boolean permit = false;
          try {
            permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
            if (permit) {
              System.out.println("Semaphore acquired");
              sleep(5);
            } else {
              System.out.println("Could not acquire semaphore");
            }
          } catch (InterruptedException e) {
            throw new IllegalStateException(e);
          } finally {
            if (permit) {
              semaphore.release();
            }
          }
        };

    IntStream.range(0, 10).forEach(i -> executor.submit(longRunningTask));

    stop(executor);
  }
}
