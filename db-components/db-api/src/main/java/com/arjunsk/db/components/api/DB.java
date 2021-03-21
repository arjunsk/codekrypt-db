package com.arjunsk.db.components.api;

import com.arjunsk.db.components.api.exceptions.DBException;
import java.io.Closeable;
import java.util.Map;

public interface DB extends Iterable<Map.Entry<byte[], byte[]>>, Closeable {

  byte[] get(byte[] key) throws DBException;

  void put(byte[] key, byte[] value) throws DBException;

  void delete(byte[] key) throws DBException;
}
