package com.arjunsk.db.components.api.domain;

import com.arjunsk.db.components.api.enums.CompressionType;

public class Options {
  private boolean createIfMissing = true;
  private boolean errorIfExists;

  private int writeBufferSize = 4 << 20;
  private int maxOpenFiles = 1000;
  private int blockSize = 4 * 1024;

  private CompressionType compressionType = CompressionType.SNAPPY;
  private boolean verifyChecksums = true;
  private long cacheSize;
}
