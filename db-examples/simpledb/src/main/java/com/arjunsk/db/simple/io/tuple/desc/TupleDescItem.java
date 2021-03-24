package com.arjunsk.db.simple.io.tuple.desc;

import com.arjunsk.db.simple.io.tuple.fileds.Type;
import java.io.Serializable;

public class TupleDescItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private Type fieldType;

  private String fieldName;

  public TupleDescItem(Type fieldType, String fieldName) {
    this.fieldType = fieldType;
    this.fieldName = fieldName;
  }

  public Type getFieldType() {
    return fieldType;
  }
}
