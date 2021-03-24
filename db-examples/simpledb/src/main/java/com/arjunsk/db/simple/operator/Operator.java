package com.arjunsk.db.simple.operator;

import com.arjunsk.db.simple.db.iterator.DbIterator;
import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.tuple.Tuple;
import java.util.NoSuchElementException;

public abstract class Operator implements DbIterator {

  private static final long serialVersionUID = 1L;
  private Tuple next = null;
  private boolean open = false;

  public void open() throws DbException, TransactionAbortedException {
    this.open = true;
  }

  public boolean hasNext() throws DbException, TransactionAbortedException {
    if (!this.open) throw new IllegalStateException("Operator not yet open");

    if (next == null) next = fetchNext();
    return next != null;
  }

  public Tuple next() throws DbException, TransactionAbortedException, NoSuchElementException {
    if (next == null) {
      next = fetchNext();
      if (next == null) throw new NoSuchElementException();
    }

    Tuple result = next;
    next = null;
    return result;
  }

  public void close() {
    next = null;
    this.open = false;
  }

  protected abstract Tuple fetchNext() throws DbException, TransactionAbortedException;
}
