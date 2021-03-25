package com.arjunsk.db.simple.io.tuple.desc;

import com.arjunsk.db.simple.io.fileds.Type;
import java.io.Serializable;
import java.util.NoSuchElementException;
/** TupleDesc describes the schema of a tuple. */
public class TupleDesc implements Serializable {

  private final int numFields;
  public TupleDescItem[] tupleDescItems;

  /**
   * Create a new TupleDesc with fieldTypes.length fields with fields of the specified types, with
   * associated named fields.
   *
   * @param fieldTypes array specifying the number of and types of fields in this TupleDesc. It must
   *     contain at least one entry.
   * @param fieldNames array specifying the names of the fields. Note that names may be null.
   */
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

  /**
   * @return The size (in bytes) of tuples corresponding to this TupleDesc. Note that tuples from a
   *     given TupleDesc are of a fixed size.
   */
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
