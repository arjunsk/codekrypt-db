package com.arjunsk.db.simple.db;

public class Permission {
  public static final Permission READ_ONLY = new Permission(0);
  public static final Permission READ_WRITE = new Permission(1);

  int permLevel;

  private Permission(int permLevel) {
    this.permLevel = permLevel;
  }

  public String toString() {
    if (permLevel == 0) return "READ_ONLY";
    if (permLevel == 1) return "READ_WRITE";
    return "UNKNOWN";
  }
}
