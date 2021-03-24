package com.arjunsk.db.simple.io.file.impl.heap;

import com.arjunsk.db.simple.db.BufferPool;
import com.arjunsk.db.simple.db.Database;
import com.arjunsk.db.simple.db.Permission;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * HeapFile is an implementation of a DbFile that stores a collection of tuples in no particular
 * order. Tuples are stored on pages, each of which is a fixed size, and the file is simply a
 * collection of those pages. HeapFile works closely with HeapPage. The format of HeapPages is
 * described in the HeapPage constructor.
 */
public class HeapFile implements DbFile {

  private final int numberOfPages;
  private final File file;
  private final TupleDesc tupleDesc;

  public HeapFile(File file, TupleDesc tupleDesc) {
    this.file = file;
    this.tupleDesc = tupleDesc;
    this.numberOfPages = (int) (file.length() / BufferPool.PAGE_SIZE);
  }

  /**
   * Read a page from the disk according to the PageId. Note that this method should only be called
   * directly in the BufferPool class. Other places where the page is needed, need to be accessed
   * through the BufferPool, so as to realize the caching function.
   */
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
  public ArrayList<Page> insertTuple(TransactionId transactionId, Tuple tuple)
      throws DbException, IOException, TransactionAbortedException {
    return null;
  }

  @Override
  public Page deleteTuple(TransactionId tid, Tuple t)
      throws DbException, TransactionAbortedException {
    return null;
  }

  @Override
  public DbFileIterator iterator(TransactionId tid) {
    return new HeapFileIterator(tid);
  }

  @Override
  public TupleDesc getTupleDesc() {
    return this.tupleDesc;
  }

  public int getId() {
    return file.getAbsoluteFile().hashCode();
  }

  /** Returns the number of pages in this HeapFile. */
  public int numPages() {
    return numberOfPages;
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
      // Load the tuples of the first page
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

    public Iterator<Tuple> getTuplesInPage(HeapPageId pageId) throws DbException {
      // You can't use the readPage method of HeapFile directly, but get the page through
      // BufferPool.
      HeapPage page =
          (HeapPage) Database.getBufferPool().getPage(transactionId, pageId, Permission.READ_ONLY);
      return page.iterator();
    }
  }
}
