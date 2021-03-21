package com.arjunsk.db.components.health.application.demon;

import com.arjunsk.db.components.health.application.AdminServer;
import com.arjunsk.db.components.health.application.domain.Node;
import java.net.InetAddress;

@SuppressWarnings({"java:S2189", "InfiniteLoopStatement"})
public class HealthCheckDemon implements Runnable {

  private static final int TIMEOUT_MILLI = 1000; // 1 sec
  private static final int HEATH_CHECK_PERIOD_MILLIS = 1000; // 1 sec

  private final AdminServer server = AdminServer.getInstance();

  // Demon Thread
  @Override
  public void run() {
    while (true) {
      try {
        sleep(HEATH_CHECK_PERIOD_MILLIS); // sleep for 1 second

        // ping all nodes
        for (Node node : server.getNodes()) {
          String myHostname = server.getMyHostname();
          if (!node.id.equals(myHostname)) {

            InetAddress inet = InetAddress.getByName(node.name);
            if (inet.isReachable(TIMEOUT_MILLI)) {
              System.out.println("Ping Node [id:" + node.id + "] ==> Node  Up!");
              server.nodeUp(node.id);
            } else {
              System.out.println("Ping Node [id:" + node.id + "] ==> Node Down!");
              server.nodeDown(node.id);
            }

          } else {
            server.nodeSelf(node.id);
          }
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
