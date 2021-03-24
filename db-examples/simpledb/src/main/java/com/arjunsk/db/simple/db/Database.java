package com.arjunsk.db.simple.db;

public class Database {
  private static final Database instance = new Database();

  private final Catalog catalog;
  private final BufferPool bufferPool;

  private Database() {
    this.catalog = new Catalog();
    this.bufferPool = new BufferPool(BufferPool.DEFAULT_PAGES);
  }

  public static Catalog getCatalog() {
    return instance.catalog;
  }

  public static BufferPool getBufferPool() {
    return instance.bufferPool;
  }
}
