package com.arjunsk.db.simple.db;

import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.io.file.impl.heap.HeapFile;
import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.page.Page;
import com.arjunsk.db.simple.io.page.PageId;
import com.arjunsk.db.simple.io.page.impl.heap.HeapPage;
import java.util.HashMap;

public class BufferPool {
  public static final int PAGE_SIZE = 4096;
  public static final int DEFAULT_PAGES = 50;

  public final int pagesNum;
  private final HashMap<PageId, Page> pid2pages;

  public BufferPool(int numPages) {
    pagesNum = numPages;
    pid2pages = new HashMap<>(pagesNum);
  }

  public void transactionComplete(TransactionId tid) {}

  public Page getPage(TransactionId tid, PageId pid, Permissions perm) throws DbException {

    if (pid2pages.containsKey(pid)) {
      return pid2pages.get(pid);
    } else {
      HeapFile table = (HeapFile) Database.getCatalog().getDbFile(pid.getTableId());
      HeapPage newPage = (HeapPage) table.readPage(pid);
      addNewPage(pid, newPage);
      return newPage;
    }
  }

  private void addNewPage(PageId pid, Page newPage) {
    pid2pages.put(pid, newPage);
    if (pid2pages.size() > pagesNum) {
      // TODO: Implement
    }
  }
}
