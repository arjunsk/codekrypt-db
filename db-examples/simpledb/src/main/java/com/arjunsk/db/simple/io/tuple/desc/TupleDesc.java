package com.arjunsk.db.simple.io.tuple.desc;

import com.arjunsk.db.simple.io.tuple.fileds.Type;
import java.io.Serializable;
import java.util.NoSuchElementException;

public class TupleDesc implements Serializable {

  private final int numFields;
  public TupleDescItem[] tupleDescItems;

  public TupleDesc(Type[] fieldTypes, String[] fieldNames) {
    if (fieldTypes.length != fieldNames.length) {
      throw new IllegalArgumentException();
    }

    numFields = fieldTypes.length;

    tupleDescItems = new TupleDescItem[fieldTypes.length];
    for (int i = 0; i < fieldTypes.length; i++) {
      tupleDescItems[i] = new TupleDescItem(fieldTypes[i], fieldNames[i]);
    }
  }

  public int getSize() {
    int totalSize = 0;
    for (TupleDescItem tupleDescItem : tupleDescItems) {
      totalSize += tupleDescItem.getFieldType().getLen();
    }
    return totalSize;
  }

  public Type getFieldType(int i) throws NoSuchElementException {
    if (i < 0 || i >= numFields) {
      throw new NoSuchElementException();
    }
    return tupleDescItems[i].getFieldType();
  }

  public int numFields() {
    return this.numFields;
  }
}
