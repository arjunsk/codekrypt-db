package com.arjunsk.db.simple.io.tuple.fileds;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public interface Field extends Serializable {

  void serialize(DataOutputStream dos) throws IOException;

  Type getType();

  int hashCode();

  boolean equals(Object field);

  String toString();
}
