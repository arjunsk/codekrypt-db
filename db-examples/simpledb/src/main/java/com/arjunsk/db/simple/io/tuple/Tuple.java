package com.arjunsk.db.simple.io.tuple;

import com.arjunsk.db.simple.io.record.RecordId;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import com.arjunsk.db.simple.io.tuple.fileds.Field;

public class Tuple {

  private TupleDesc tupleDesc;

  private Field[] fields;

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
}
