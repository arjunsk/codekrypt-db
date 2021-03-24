package com.arjunsk.db.simple.db.iterator.impl;

import com.arjunsk.db.simple.db.Database;
import com.arjunsk.db.simple.db.iterator.DbIterator;
import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.file.DbFileIterator;
import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.util.NoSuchElementException;

public class SeqScan implements DbIterator {

  private TransactionId transactionId;
  private int tableId;
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