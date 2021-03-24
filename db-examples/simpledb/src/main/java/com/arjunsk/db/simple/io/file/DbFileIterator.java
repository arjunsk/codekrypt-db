package com.arjunsk.db.simple.io.file;

import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.tuple.Tuple;
import java.util.NoSuchElementException;

/** DbFileIterator is the iterator interface that all SimpleDB DbFile should implement. */
public interface DbFileIterator {

  void open() throws DbException, TransactionAbortedException;

  boolean hasNext() throws DbException;

  Tuple next() throws DbException, NoSuchElementException;

  void rewind() throws DbException, TransactionAbortedException;

  void close();
}
