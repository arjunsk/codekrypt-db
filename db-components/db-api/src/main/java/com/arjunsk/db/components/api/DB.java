package com.arjunsk.db.components.api;

import com.arjunsk.db.components.api.exceptions.DBException;
import java.io.Closeable;
import java.util.Map;

public interface DB extends Iterable<Map.Entry<byte[], byte[]>>, Closeable {

  byte[] get(byte[] key) throws DBException;

  void put(byte[] key, byte[] value) throws DBException;

  void delete(byte[] key) throws DBException;

  /**
   * ### BATCH SUPPORT ###
   *
   * <p>Usage:
   *
   * <pre>
   *  WriteBatch batch = db.createWriteBatch();
   *  batch.put(bytes("key1"),bytes("value1"));
   *  batch.put(bytes("key2"),bytes("value2"));
   *  batch.delete(bytes("key3"));
   *  db.write(batch);
   * </pre>
   */
  WriteBatch createWriteBatch();

  void write(WriteBatch updates) throws DBException;
}
