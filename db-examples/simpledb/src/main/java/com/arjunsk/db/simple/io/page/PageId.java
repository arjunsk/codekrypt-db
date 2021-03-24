package com.arjunsk.db.simple.io.page;

import java.io.Serializable;

public interface PageId extends Serializable {
  int[] serialize();

  int pageNumber();

  int getTableId();

  boolean equals(Object o);

  int hashCode();
}
