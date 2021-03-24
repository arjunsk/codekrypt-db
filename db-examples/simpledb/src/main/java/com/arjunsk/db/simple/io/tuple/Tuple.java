package com.arjunsk.db.simple.io.tuple;

import com.arjunsk.db.simple.io.record.RecordId;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import com.arjunsk.db.simple.io.tuple.fileds.Field;
import java.io.Serializable;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a specified schema
 * specified by a TupleDesc object and contain Field objects with the data for each field.
 */
public class Tuple implements Serializable {

  private static final long serialVersionUID = 1L;

  private final TupleDesc tupleDesc;

  private final Field[] fields;

  private RecordId recordId;

  public Tuple(TupleDesc td) {
    this.tupleDesc = td;
    fields = new Field[td.numFields()];
  }

  public void setRecordId(RecordId recordId) {
    this.recordId = recordId;
  }

  public void setField(int fieldIndex, Field fieldValue) {
    if (!isValidIndex(fieldIndex)) {
      throw new IllegalArgumentException("Field Index Not Valid");
    }
    fields[fieldIndex] = fieldValue;
  }

  private boolean isValidIndex(int index) {
    return index >= 0 && index < fields.length;
  }

  public String toString() {
    StringBuilder rowString = new StringBuilder();
    for (int i = 0; i < fields.length; i++) {
      if (i == fields.length - 1) {
        rowString.append(fields[i].toString()).append("\n");
      } else {
        rowString.append(fields[i].toString()).append("\t");
      }
    }
    return rowString.toString();
  }

  public Field getField(int i) {
    if (!isValidIndex(i)) {
      throw new IllegalArgumentException();
    }
    return fields[i];
  }
}
