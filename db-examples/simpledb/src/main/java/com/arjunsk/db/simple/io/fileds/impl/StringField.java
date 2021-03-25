package com.arjunsk.db.simple.io.fileds.impl;

import com.arjunsk.db.simple.io.fileds.Field;
import com.arjunsk.db.simple.io.fileds.Type;
import java.io.DataOutputStream;
import java.io.IOException;

/** Instance of Field that stores a single String of a fixed length. */
public class StringField implements Field {

  private final String value;

  private final int maxSize;

  public StringField(String stringValue, int maxSize) {
    this.maxSize = maxSize;

    if (stringValue.length() > maxSize) value = stringValue.substring(0, maxSize);
    else value = stringValue;
  }

  @Override
  public void serialize(DataOutputStream dos) throws IOException {
    String stringValue = value;
    int overflow = maxSize - stringValue.length();
    if (overflow < 0) {
      stringValue = stringValue.substring(0, maxSize);
    }
    dos.writeInt(stringValue.length());
    dos.writeBytes(stringValue);
    while (overflow-- > 0) dos.write((byte) 0);
  }

  @Override
  public Type getType() {
    return Type.STRING_TYPE;
  }

  @Override
  public String toString() {
    return value;
  }
}
