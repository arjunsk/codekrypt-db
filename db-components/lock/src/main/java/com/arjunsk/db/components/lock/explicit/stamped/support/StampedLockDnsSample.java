package com.arjunsk.db.components.lock.explicit.stamped.support;

import static com.arjunsk.db.components.utils.ConcurrentUtils.sleep;
import static com.arjunsk.db.components.utils.ConcurrentUtils.stop;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/** More to read: <code>tryConvertToWriteLock()</code>, <code>tryOptimisticRead</code> */
public class StampedLockDnsSample {

  private final ExecutorService executor;
  private final Map<String, String> dnsMap;
  private final StampedLock lock;

  public StampedLockDnsSample() {
    this.executor = Executors.newFixedThreadPool(2);
    dnsMap = new HashMap<>();
    lock = new StampedLock();
  }

  public void registerNewNode(String domain, String ip) {

    executor.submit(
        () -> {
          long stamp = lock.writeLock(); // NOTE
          try {
            sleep(1);
            dnsMap.put(domain, ip);
          } finally {
            lock.unlockWrite(stamp); // NOTE
          }
        });
  }

  public void printCurrentNodes(String domain) {
    Runnable readTask =
        () -> {
          long stamp = lock.readLock(); // NOTE
          try {
            System.out.println(dnsMap.get(domain));
            sleep(1);
          } finally {
            lock.unlockRead(stamp); // NOTE
          }
        };

    executor.submit(readTask);
  }

  public void stopServer() {
    stop(executor);
  }
}
