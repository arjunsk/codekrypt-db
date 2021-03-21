package com.arjunsk.db.components.lock.implicit.readwrite;

import com.arjunsk.db.components.lock.implicit.readwrite.support.ReadWriteLockDnsSample;

/**
 * @see <a
 *     href="https://winterbe.com/posts/2015/04/30/java8-concurrency-tutorial-synchronized-locks-examples/">ReadWriteLock</a>
 */
public class ReadWriterLockDriver {

  public static void main(String[] args) {
    ReadWriteLockDnsSample dnsServer = new ReadWriteLockDnsSample();

    dnsServer.registerNewNode("google.com", "123.123.123.4");
    dnsServer.registerNewNode("amazon.com", "124.123.123.4");

    dnsServer.printCurrentNodes("google.com");
    dnsServer.printCurrentNodes("amazon.com");

    dnsServer.stopServer();
  }
}
