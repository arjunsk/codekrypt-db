package com.arjunsk.db.simple.io.record;

import com.arjunsk.db.simple.io.page.PageId;
import java.io.Serializable;

public class RecordId implements Serializable {

  private static final long serialVersionUID = 1L;

  private final PageId pageId;

  private final int tupleNumber;

  public RecordId(PageId pid, int tupleNumber) {
    pageId = pid;
    this.tupleNumber = tupleNumber;
  }

  public int tupleNumber() {
    return tupleNumber;
  }

  public PageId getPageId() {
    return pageId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (o instanceof RecordId) {
      RecordId another = (RecordId) o;
      return pageId.equals(another.getPageId()) && another.tupleNumber() == tupleNumber;
    } else return false;
  }

  @Override
  public int hashCode() {
    return 31 * pageId.hashCode() + tupleNumber;
  }
}
