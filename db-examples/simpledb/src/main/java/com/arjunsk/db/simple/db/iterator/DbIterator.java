package com.arjunsk.db.simple.db.iterator;

import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.io.Serializable;
import java.util.NoSuchElementException;

public interface DbIterator extends Serializable {

  void open() throws DbException, TransactionAbortedException;

  boolean hasNext() throws DbException, TransactionAbortedException;

  Tuple next() throws DbException, NoSuchElementException, TransactionAbortedException;

  void rewind() throws DbException, TransactionAbortedException;

  TupleDesc getTupleDesc();

  void close();
}
