package com.arjunsk.db.components.health.application;

import com.arjunsk.db.components.health.application.domain.Node;
import com.arjunsk.db.components.health.application.demon.HealthCheckDemon;
import java.net.InetAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class AdminServer {

  private static AdminServer adminServer;

  private final ConcurrentHashMap<String, Node> nodes = new ConcurrentHashMap<>();
  private String myHostName = "";

  public static void startup() {
    try {
      // Start restlet (It provides the health check ip)

      // Attach AdminObject to the restlet
      AdminServer.getInstance();

      // start Ping Checks Thread to monitor cluster status
      HealthCheckDemon healthCheck = new HealthCheckDemon();
      new Thread(healthCheck).start();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static synchronized AdminServer getInstance() {
    if (adminServer == null) {
      adminServer = new AdminServer();
      adminServer.initConfig();
    }
    return adminServer;
  }

  public void initConfig() {

    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      myHostName = inetAddress.getHostName();

      // Register Master Node
      registerNode(myHostName, "localhost");

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void registerNode(String id, String name) {
    // register node name
    Node node = new Node();
    node.id = id;
    node.name = name;
    nodes.put(id, node);
    System.out.println("Registered Node: " + id + " as: " + name);
  }

  public String getMyHostname() {
    return this.myHostName;
  }

  public Collection<Node> getNodes() {
    return nodes.values();
  }

  public void nodeUp(String id) {
    Node node = nodes.get(id);
    node.status = "up";
  }

  public void nodeDown(String id) {
    Node node = nodes.get(id);
    node.status = "down";
  }

  public void nodeSelf(String id) {
    Node node = nodes.get(id);
    node.status = "self";
  }
}
