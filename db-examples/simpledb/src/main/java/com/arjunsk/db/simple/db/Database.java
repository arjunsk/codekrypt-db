package com.arjunsk.db.simple.db;

/** The Database Static class. */
public class Database {

  private static final Database instance = new Database();

  private final Catalog catalog;
  private final BufferPool bufferPool;

  private Database() {
    this.catalog = new Catalog();
    this.bufferPool = new BufferPool(BufferPool.DEFAULT_PAGES);
  }

  /** Note that we are referring to the fields of static object. */
  public static Catalog getCatalog() {
    return instance.catalog;
  }

  /** Note that we are referring to the fields of static object. */
  public static BufferPool getBufferPool() {
    return instance.bufferPool;
  }
}
