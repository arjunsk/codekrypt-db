package com.arjunsk.db.components.api;

import java.io.Closeable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public interface DBIterator extends Iterator<Entry<byte[], byte[]>>, Closeable {
  /**
   * Repositions the iterator so the key of the next BlockElement returned greater than or equal to
   * the specified targetKey.
   */
  void seek(byte[] key);

  /** Repositions the iterator so is is at the beginning of the Database. */
  void seekToFirst();

  /** Returns the next element in the iteration, without advancing the iteration. */
  Map.Entry<byte[], byte[]> peekNext();

  /** Repositions the iterator so it is at the end of of the Database. */
  void seekToLast();
}
