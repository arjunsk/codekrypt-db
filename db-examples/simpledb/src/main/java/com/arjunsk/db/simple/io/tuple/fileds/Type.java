package com.arjunsk.db.simple.io.tuple.fileds;

import com.arjunsk.db.simple.io.tuple.fileds.impl.IntField;
import com.arjunsk.db.simple.io.tuple.fileds.impl.StringField;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.ParseException;

public enum Type {
  INT_TYPE() {
    @Override
    public int getLen() {
      return 4;
    }

    @Override
    public Field parse(DataInputStream dis) throws ParseException {
      try {
        return new IntField(dis.readInt());
      } catch (IOException e) {
        throw new ParseException("couldn't parse", 0);
      }
    }
  },
  STRING_TYPE() {
    @Override
    public int getLen() {
      return STRING_LEN + 4;
    }

    @Override
    public Field parse(DataInputStream dis) throws ParseException {
      try {
        int strLen = dis.readInt();
        byte[] bs = new byte[strLen];
        dis.read(bs);
        dis.skipBytes(STRING_LEN - strLen);
        return new StringField(new String(bs), STRING_LEN);
      } catch (IOException e) {
        throw new ParseException("couldn't parse", 0);
      }
    }
  };

  public static final int STRING_LEN = 128;

  public abstract int getLen();

  public abstract Field parse(DataInputStream dis) throws ParseException;
}
