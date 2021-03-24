package com.arjunsk.db.simple.io.file;

import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.page.Page;
import com.arjunsk.db.simple.io.page.PageId;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The interface for database files on disk. Each table is represented by a single DbFile. DbFiles
 * can fetch pages and iterate through tuples. Each file has a unique id used to store metadata
 * about the table in the Catalog. DbFiles are generally accessed through the buffer pool, rather
 * than directly by operators.
 */
public interface DbFile {

  /** Read the specified page from disk. */
  Page readPage(PageId id) throws IllegalArgumentException;

  /** Push the specified page to disk. */
  void writePage(Page p) throws IOException;

  /**
   * Inserts the specified tuple to the file on behalf of transaction. This method will acquire a
   * lock on the affected pages of the file, and may block until the lock can be acquired.
   */
  ArrayList<Page> insertTuple(TransactionId transactionId, Tuple tuple)
      throws DbException, IOException, TransactionAbortedException;

  /**
   * Removes the specified tuple from the file on behalf of the specified transaction. This method
   * will acquire a lock on the affected pages of the file, and may block until the lock can be
   * acquired.
   */
  Page deleteTuple(TransactionId tid, Tuple t) throws DbException, TransactionAbortedException;

  /**
   * Returns an iterator over all the tuples stored in this DbFile. The iterator must use {@link
   * com.arjunsk.db.simple.db.BufferPool#getPage}, rather than {@link #readPage} to iterate through
   * the pages.
   */
  DbFileIterator iterator(TransactionId tid);

  /**
   * Returns a unique ID used to identify this DbFile in the Catalog. This id can be used to look up
   * the table via {@link com.arjunsk.db.simple.db.Catalog#getDbFile} and {@link
   * com.arjunsk.db.simple.db.Catalog#getTupleDesc}.
   */
  int getId();

  /** Returns the TupleDesc of the table stored in this DbFile. */
  TupleDesc getTupleDesc();
}
