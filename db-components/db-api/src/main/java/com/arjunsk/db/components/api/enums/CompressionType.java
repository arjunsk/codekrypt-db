package com.arjunsk.db.components.api.enums;

public enum CompressionType {
  NONE(0x00),
  SNAPPY(0x01);

  private final int persistentId;

  CompressionType(int persistentId) {
    this.persistentId = persistentId;
  }

  public int persistentId() {
    return persistentId;
  }
}
