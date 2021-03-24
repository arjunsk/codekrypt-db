package com.arjunsk.db.simple.io.page.impl.heap;

import com.arjunsk.db.simple.io.page.PageId;

public class HeapPageId implements PageId {

  private final int tableId;

  private final int pageNum;

  public HeapPageId(int tableId, int pgNo) {
    this.tableId = tableId;
    this.pageNum = pgNo;
  }

  @Override
  public int[] serialize() {
    int[] data = new int[2];

    data[0] = getTableId();
    data[1] = pageNumber();

    return data;
  }

  @Override
  public int pageNumber() {
    return 0;
  }

  public int getTableId() {
    return this.tableId;
  }

  @Override
  public int hashCode() {
    return 31 * tableId + pageNum;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof PageId) {
      PageId another = (PageId) o;
      return this.pageNum == another.pageNumber() && this.tableId == another.getTableId();
    }
    return false;
  }
}
