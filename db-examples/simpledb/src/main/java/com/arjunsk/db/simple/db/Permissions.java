package com.arjunsk.db.simple.db;

public class Permissions {
  public static final Permissions READ_ONLY = new Permissions(0);
  public static final Permissions READ_WRITE = new Permissions(1);

  int permLevel;

  private Permissions(int permLevel) {
    this.permLevel = permLevel;
  }

  public String toString() {
    if (permLevel == 0) return "READ_ONLY";
    if (permLevel == 1) return "READ_WRITE";
    return "UNKNOWN";
  }
}
