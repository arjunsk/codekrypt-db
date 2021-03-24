package com.arjunsk.db.simple.operator.impl;

import com.arjunsk.db.simple.exception.DbException;
import com.arjunsk.db.simple.exception.TransactionAbortedException;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import com.arjunsk.db.simple.operator.Operator;

public class Insert extends Operator {

  @Override
  protected Tuple fetchNext() throws DbException, TransactionAbortedException {
    return null;
  }

  @Override
  public void rewind() throws DbException {

  }

  @Override
  public TupleDesc getTupleDesc() {
    return null;
  }
}
