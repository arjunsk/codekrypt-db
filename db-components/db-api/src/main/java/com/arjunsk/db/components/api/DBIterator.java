/*
 * Copyright (C) 2011 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
