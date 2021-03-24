package com.arjunsk.db.components.api;

import com.arjunsk.db.components.api.exceptions.DBException;
import java.io.Closeable;
import java.util.Map;

public interface DB extends Iterable<Map.Entry<byte[], byte[]>>, Closeable {

  byte[] get(byte[] key) throws DBException;

  void put(byte[] key, byte[] value) throws DBException;

  void delete(byte[] key) throws DBException;

  /**
   * ### ITERATOR ####
   *
   * <p>Usage 1
   *
   * <pre>
   *   DBIterator iterator = db.iterator();
   *   iterator.seek(bytes("key")); // starts from the specified key
   *   iterator.seekToFirst(); // starts from the first key
   *   iterator.seekToLast(); // starts from the last key
   * </pre>
   *
   * <p>Usage 2
   *
   * <pre>
   * while (iterator.hasNext()){
   *     byte[] key = iterator.peekNext().getKey();
   *     byte[] value = iterator.peekNext().getValue();
   *
   *     // whatever you want to do
   *     iterator.next();
   * }
   * iterator.close();
   *
   * </pre>
   */
  @Override
  DBIterator iterator();

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
