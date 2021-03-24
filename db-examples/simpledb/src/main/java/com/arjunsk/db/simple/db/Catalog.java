package com.arjunsk.db.simple.db;

import com.arjunsk.db.simple.io.file.DbFile;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Each table will have a separate file.
 *
 * <p>Catalog manages the tables inside a Database.
 */
public class Catalog {

  // Maps TableID to DbFile
  private final Map<Integer, DbFile> id2File;

  // Maps TableID to TableName
  private final Map<Integer, String> id2Name;

  // Maps TableID to Table's Primary Key
  private final Map<Integer, String> id2PrimaryKey;

  public Catalog() {
    this.id2File = new HashMap<>();
    this.id2Name = new HashMap<>();
    this.id2PrimaryKey = new HashMap<>();
  }

  public void addTable(DbFile file, String name, String primaryKey) {

    if (name == null || primaryKey == null) {
      throw new IllegalArgumentException();
    }

    Integer tableId = file.getId();

    id2File.put(tableId, file);
    id2Name.put(tableId, name);
    id2PrimaryKey.put(tableId, primaryKey);
  }

  public void addTable(DbFile file, String name) {
    addTable(file, name, "_id");
  }

  public void addTable(DbFile file) {
    addTable(file, (UUID.randomUUID()).toString());
  }

  public DbFile getDbFile(Integer tableId) {
    if (isTableNotRegistered(tableId, id2File)) {
      throw new NoSuchElementException();
    }
    return id2File.get(tableId);
  }

  public TupleDesc getTupleDesc(int tableId) throws NoSuchElementException {
    if (isTableNotRegistered(tableId, id2File)) {
      throw new NoSuchElementException();
    }
    return id2File.get(tableId).getTupleDesc();
  }

  private boolean isTableNotRegistered(int id, Map<?, ?> map) {
    return !map.containsKey(id);
  }
}
