package com.arjunsk.db.simple.io.file.impl.heap;

import com.arjunsk.db.simple.db.BufferPool;
import com.arjunsk.db.simple.db.Database;
import com.arjunsk.db.simple.db.Permissions;
import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.file.DbFile;
import com.arjunsk.db.simple.io.file.DbFileIterator;
import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.page.Page;
import com.arjunsk.db.simple.io.page.PageId;
import com.arjunsk.db.simple.io.page.impl.heap.HeapPage;
import com.arjunsk.db.simple.io.page.impl.heap.HeapPageId;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HeapFile implements DbFile {

  private final int numPage;
  private File file;
  private TupleDesc tupleDesc;

  public HeapFile(File file, TupleDesc tupleDesc) {
    this.file = file;
    this.tupleDesc = tupleDesc;
    this.numPage = (int) (file.length() / BufferPool.PAGE_SIZE);
  }

  @Override
  public Page readPage(PageId pageId) throws IllegalArgumentException {
    Page page = null;
    byte[] data = new byte[BufferPool.PAGE_SIZE];

    try (RandomAccessFile randomAccessFile = new RandomAccessFile(getFile(), "r")) {
      int pos = pageId.pageNumber() * BufferPool.PAGE_SIZE;
      randomAccessFile.seek(pos);
      randomAccessFile.read(data, 0, data.length);
      page = new HeapPage((HeapPageId) pageId, data);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return page;
  }

  private File getFile() {
    return file;
  }

  @Override
  public void writePage(Page p) throws IOException {}

  @Override
  public DbFileIterator iterator(TransactionId tid) {
    return new HeapFileIterator(tid);
  }

  public int getId() {
    return file.getAbsoluteFile().hashCode();
  }

  @Override
  public TupleDesc getTupleDesc() {
    return this.tupleDesc;
  }

  public int numPages() {
    return numPage;
  }

  private class HeapFileIterator implements DbFileIterator {

    private final TransactionId transactionId;
    private Iterator<Tuple> tuplesInPage;
    private int pagePos;

    public HeapFileIterator(TransactionId transactionId) {
      this.transactionId = transactionId;
    }

    @Override
    public void open() throws DbException, TransactionAbortedException {
      pagePos = 0;
      HeapPageId pid = new HeapPageId(getId(), pagePos);
      tuplesInPage = getTuplesInPage(pid);
    }

    @Override
    public boolean hasNext() throws DbException {
      if (tuplesInPage == null) {
        return false;
      }
      if (tuplesInPage.hasNext()) {
        return true;
      }
      if (pagePos < numPages() - 1) {
        pagePos++;
        HeapPageId pid = new HeapPageId(getId(), pagePos);
        tuplesInPage = getTuplesInPage(pid);
        return tuplesInPage.hasNext();
      }
      return false;
    }

    @Override
    public Tuple next() throws DbException, NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException("not opened or no tuple remained");
      }
      return tuplesInPage.next();
    }

    @Override
    public void rewind() throws DbException, TransactionAbortedException {
      open();
    }

    @Override
    public void close() {
      pagePos = 0;
      tuplesInPage = null;
    }

    public Iterator<Tuple> getTuplesInPage(HeapPageId pid) throws DbException {
      HeapPage page =
          (HeapPage) Database.getBufferPool().getPage(transactionId, pid, Permissions.READ_ONLY);
      return page.iterator();
    }
  }
}
