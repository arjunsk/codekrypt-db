package com.arjunsk.db.simple.io.id;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/** TransactionId is a class that contains the identifier of a transaction. */
public class TransactionId implements Serializable {

  static AtomicLong counter = new AtomicLong(0);
  long value;

  public TransactionId() {
    value = counter.getAndIncrement();
  }

  public boolean equals(Object tid) {
    return ((TransactionId) tid).value == value;
  }

  public int hashCode() {
    return (int) value;
  }
}
