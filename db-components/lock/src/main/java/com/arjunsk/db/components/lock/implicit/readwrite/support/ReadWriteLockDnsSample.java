package com.arjunsk.db.components.lock.implicit.readwrite.support;

import static com.arjunsk.db.components.utils.ConcurrentUtils.sleep;
import static com.arjunsk.db.components.utils.ConcurrentUtils.stop;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDnsSample {

  private final ExecutorService executor;
  private final Map<String, String> dnsMap;
  private final ReadWriteLock lock;

  public ReadWriteLockDnsSample() {
    this.executor = Executors.newFixedThreadPool(2);
    dnsMap = new HashMap<>();
    lock = new ReentrantReadWriteLock();
  }

  public void registerNewNode(String domain, String ip) {

    executor.submit(
        () -> {
          lock.writeLock().lock();
          try {
            sleep(1);
            dnsMap.put(domain, ip);
          } finally {
            lock.writeLock().unlock();
          }
        });
  }

  public void printCurrentNodes(String domain) {
    Runnable readTask =
        () -> {
          lock.readLock().lock();
          try {
            System.out.println(dnsMap.get(domain));
            sleep(1);
          } finally {
            lock.readLock().unlock();
          }
        };

    executor.submit(readTask);
  }

  public void stopServer() {
    stop(executor);
  }
}
