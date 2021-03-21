package com.arjunsk.db.components.lock.explicit.stamped;

import com.arjunsk.db.components.lock.explicit.stamped.support.StampedLockDnsSample;

public class StampedLockDriver {

  public static void main(String[] args) {
    StampedLockDnsSample dnsServer = new StampedLockDnsSample();

    dnsServer.registerNewNode("google.com", "123.123.123.4");
    dnsServer.registerNewNode("amazon.com", "124.123.123.4");

    dnsServer.printCurrentNodes("google.com");
    dnsServer.printCurrentNodes("amazon.com");

    dnsServer.stopServer();
  }
}
