package com.arjunsk.db.simple.operator.impl;

import com.arjunsk.db.simple.db.Database;
import com.arjunsk.db.simple.operator.DbIterator;
import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.file.DbFileIterator;
import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.util.NoSuchElementException;

/**
 * SeqScan is an implementation of a sequential scan access method that reads each tuple of a table
 * in no particular order (e.g., as they are laid out on disk).
 */
public class SeqScan implements DbIterator {

  private final TransactionId transactionId;
  private final int tableId;
  private DbFileIterator dbFileIterator;

  public SeqScan(TransactionId transactionId, int tableId) {
    this.transactionId = transactionId;
    this.tableId = tableId;
    this.dbFileIterator = Database.getCatalog().getDbFile(tableId).iterator(transactionId);
  }

  @Override
  public void open() throws DbException, TransactionAbortedException {
    dbFileIterator.open();
  }

  @Override
  public boolean hasNext() throws DbException {
    return dbFileIterator.hasNext();
  }

  @Override
  public Tuple next() throws DbException, NoSuchElementException {
    return dbFileIterator.next();
  }

  @Override
  public void rewind() throws DbException, TransactionAbortedException {
    dbFileIterator.rewind();
  }

  @Override
  public TupleDesc getTupleDesc() {
    return null;
  }

  @Override
  public void close() {
    dbFileIterator.close();
  }
}
