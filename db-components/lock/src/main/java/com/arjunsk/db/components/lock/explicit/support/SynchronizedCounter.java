package com.arjunsk.db.components.lock.explicit.support;

import static com.arjunsk.db.components.utils.ConcurrentUtils.stop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SynchronizedCounter {

  public int count;

  public SynchronizedCounter() {
    this.count = 0;
  }

  public void compute() {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    IntStream.range(0, 10000).forEach(i -> executor.submit(this::incrementSync));

    stop(executor);

    System.out.println(count); // 10000
  }

  private void incrementSync() {
    synchronized (this) {
      count = count + 1;
    }
  }
}
