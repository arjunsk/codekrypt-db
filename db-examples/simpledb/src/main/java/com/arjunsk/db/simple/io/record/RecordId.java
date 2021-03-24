package com.arjunsk.db.simple.io.record;

import com.arjunsk.db.simple.io.page.PageId;
import java.io.Serializable;

/** A RecordId is a reference to a specific tuple on a specific page of a specific table. */
public class RecordId implements Serializable {

  private static final long serialVersionUID = 1L;

  private final PageId pageId;

  private final int tupleNumber;

  /**
   * Creates a new RecordId referring to the specified PageId and tuple number.
   *
   * @param pageId the pageId of the page on which the tuple resides
   * @param tupleNumber the tuple number within the page.
   */
  public RecordId(PageId pageId, int tupleNumber) {
    this.pageId = pageId;
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
