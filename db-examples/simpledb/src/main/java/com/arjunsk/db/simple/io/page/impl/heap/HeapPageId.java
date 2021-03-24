package com.arjunsk.db.simple.io.page.impl.heap;

import com.arjunsk.db.simple.io.page.PageId;

/** Unique identifier for HeapPage objects. */
public class HeapPageId implements PageId {

  private final int tableId;

  private final int pageNumber;

  /** Constructor. Create a page id structure for a specific page of a specific table. */
  public HeapPageId(int tableId, int pgNo) {
    this.tableId = tableId;
    this.pageNumber = pgNo;
  }

  /**
   * Return a representation of this object as an array of integers, for writing to disk. Size of
   * returned array must contain number of integers that corresponds to number of args to one of the
   * constructors.
   */
  @Override
  public int[] serialize() {
    int[] data = new int[2];

    data[0] = getTableId();
    data[1] = pageNumber();

    return data;
  }

  @Override
  public int pageNumber() {
    return pageNumber;
  }

  public int getTableId() {
    return this.tableId;
  }

  @Override
  public int hashCode() {
    return 31 * tableId + pageNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof PageId) {
      PageId another = (PageId) o;
      return this.pageNumber == another.pageNumber() && this.tableId == another.getTableId();
    }
    return false;
  }
}
