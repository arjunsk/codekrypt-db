package com.arjunsk.db.simple.io.fileds.impl;

import com.arjunsk.db.simple.io.fileds.Field;
import com.arjunsk.db.simple.io.fileds.Type;
import java.io.DataOutputStream;
import java.io.IOException;

/** Instance of Field that stores a single integer. */
public class IntField implements Field {

  private final int value;

  public IntField(int intValue) {
    value = intValue;
  }

  @Override
  public void serialize(DataOutputStream dos) throws IOException {
    dos.writeInt(value);
  }

  @Override
  public Type getType() {
    return Type.INT_TYPE;
  }

  public String toString() {
    return Integer.toString(value);
  }

  public int hashCode() {
    return value;
  }

  public boolean equals(Object field) {
    return ((IntField) field).value == value;
  }
}
