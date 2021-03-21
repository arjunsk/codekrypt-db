package com.arjunsk.db.components.health;

import com.arjunsk.db.components.health.application.AdminServer;

public class ApplicationDriver {

  public static void main(String[] args) {
    AdminServer.startup();

    AdminServer.getInstance().registerNode("node_1", "arjunsk.com");
    AdminServer.getInstance().registerNode("node_2", "gmail.com");
    AdminServer.getInstance().registerNode("node_3", "github.com");
  }
}
