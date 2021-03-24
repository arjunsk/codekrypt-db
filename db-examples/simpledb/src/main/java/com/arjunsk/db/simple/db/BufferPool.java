package com.arjunsk.db.simple.db;

import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.io.file.impl.heap.HeapFile;
import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.page.Page;
import com.arjunsk.db.simple.io.page.PageId;
import com.arjunsk.db.simple.io.page.impl.heap.HeapPage;
import java.util.HashMap;

/**
 * BufferPool manages the reading and writing of pages into memory from disk. Access methods call
 * into it to retrieve pages, and it fetches pages from the appropriate location.
 */
public class BufferPool {

  public static final int PAGE_SIZE = 4096;
  public static final int DEFAULT_PAGES = 50;

  public final int pagesNum;
  private final HashMap<PageId, Page> pid2pages;

  public BufferPool(int numPages) {
    pagesNum = numPages;
    pid2pages = new HashMap<>(pagesNum);
  }

  public Page getPage(TransactionId transactionId, PageId pageId, Permission permission)
      throws DbException {

    if (pid2pages.containsKey(pageId)) {
      return pid2pages.get(pageId);
    } else {
      HeapFile table = (HeapFile) Database.getCatalog().getDbFile(pageId.getTableId());
      HeapPage newPage = (HeapPage) table.readPage(pageId);
      addNewPage(pageId, newPage);
      return newPage;
    }
  }

  private void addNewPage(PageId pid, Page newPage) {
    pid2pages.put(pid, newPage);
    if (pid2pages.size() > pagesNum) {
      // TODO: Implement
    }
  }

  public void transactionComplete(TransactionId tid) {
    // TODO: Implement
  }
}
