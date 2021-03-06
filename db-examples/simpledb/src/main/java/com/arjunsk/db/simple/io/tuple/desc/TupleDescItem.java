package com.arjunsk.db.simple.io.tuple.desc;

import com.arjunsk.db.simple.io.fileds.Type;
import java.io.Serializable;

public class TupleDescItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Type fieldType;

  private final String fieldName;

  public TupleDescItem(Type fieldType, String fieldName) {
    this.fieldType = fieldType;
    this.fieldName = fieldName;
  }

  public Type getFieldType() {
    return fieldType;
  }
}
