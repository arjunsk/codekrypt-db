package com.arjunsk.db.simple.io.tuple.fileds;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/** Interface for values of fields in tuples in SimpleDB. */
public interface Field extends Serializable {

  /**
   * Write the bytes representing this field to the specified DataOutputStream.
   *
   * @see DataOutputStream
   * @param dos The DataOutputStream to write to.
   */
  void serialize(DataOutputStream dos) throws IOException;

  /**
   * Returns the type of this field (see {@link Type#INT_TYPE} or {@link Type#STRING_TYPE}
   *
   * @return type of this field
   */
  Type getType();

  /**
   * Hash code. Different Field objects representing the same value should probably return the same
   * hashCode.
   */
  int hashCode();

  boolean equals(Object field);

  String toString();
}
