package com.arjunsk.db.simple.db;

import com.arjunsk.db.simple.io.file.DbFile;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Catalog {

  private final Map<Integer, DbFile> id2File;
  private final Map<Integer, String> id2Name;
  private final HashMap<Integer, String> id2PrimaryKey;
  private final HashMap<String, Integer> name2Id;

  public Catalog() {
    this.id2File = new HashMap<>();
    this.id2Name = new HashMap<>();
    this.id2PrimaryKey = new HashMap<>();
    this.name2Id = new HashMap<>();
  }

  public void addTable(DbFile file, String name, String primaryKey) {

    if (name == null || primaryKey == null) {
      throw new IllegalArgumentException();
    }

    Integer tableId = file.getId();

    if (name2Id.containsKey(name)) {
      int id = name2Id.get(name);
      id2File.remove(id);
      id2Name.remove(id);
      id2PrimaryKey.remove(id);
      name2Id.remove(name);
    }
    id2File.put(tableId, file);
    id2Name.put(tableId, name);
    id2PrimaryKey.put(tableId, primaryKey);
    name2Id.put(name, tableId);
  }

  public void addTable(DbFile file, String name) {
    addTable(file, name, "");
  }

  public void addTable(DbFile file) {
    addTable(file, (UUID.randomUUID()).toString());
  }

  public DbFile getDbFile(Integer tableId) {
    if (!isIdValid(tableId, id2File)) {
      throw new NoSuchElementException();
    }
    return id2File.get(tableId);
  }

  public TupleDesc getTupleDesc(int tableId) throws NoSuchElementException {
    if (!isIdValid(tableId, id2File)) {
      throw new NoSuchElementException();
    }
    return id2File.get(tableId).getTupleDesc();
  }

  private boolean isIdValid(int id, Map<?, ?> map) {
    return map.containsKey(id);
  }
}
