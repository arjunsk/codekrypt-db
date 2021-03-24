package com.arjunsk.db.simple.db.iterator;

import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * DbIterator is the iterator interface that all SimpleDB operators should implement. If the
 * iterator is not open, none of the methods should work, and should throw an IllegalStateException.
 * In addition to any resource allocation/de-allocation, an open method should call any child
 * iterator open methods, and in a close method, an iterator should call its children's close
 * methods.
 */
public interface DbIterator extends Serializable {

  /** Opens the iterator. This must be called before any of the other methods. */
  void open() throws DbException, TransactionAbortedException;

  /** Returns true if the iterator has more tuples. */
  boolean hasNext() throws DbException, TransactionAbortedException;

  /**
   * Returns the next tuple from the operator (typically implementing by reading from a child
   * operator or an access method).
   */
  Tuple next() throws DbException, NoSuchElementException, TransactionAbortedException;

  /** Resets the iterator to the start. */
  void rewind() throws DbException, TransactionAbortedException;

  /** Returns the TupleDesc associated with this DbIterator. */
  TupleDesc getTupleDesc();

  /**
   * Closes the iterator. When the iterator is closed, calling next(), hasNext(), or rewind() should
   * fail by throwing IllegalStateException.
   */
  void close();
}
