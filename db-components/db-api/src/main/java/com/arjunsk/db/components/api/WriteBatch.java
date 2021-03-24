package com.arjunsk.db.components.api;

import java.io.Closeable;

public interface WriteBatch extends Closeable {

  WriteBatch put(byte[] key, byte[] value);

  WriteBatch delete(byte[] key);
}
