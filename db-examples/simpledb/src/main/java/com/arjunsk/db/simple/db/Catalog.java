package com.arjunsk.db.simple.db;

import com.arjunsk.db.simple.io.file.DBFile;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Catalog {

  private final Map<Integer, DBFile> id2File;
  private final Map<Integer, String> id2Name;

  public Catalog() {
    this.id2File = new HashMap<>();
    this.id2Name = new HashMap<>();
  }

  public void addTable(DBFile file, String name) {

    Integer tableId = file.getId();

    this.id2File.put(tableId, file);
    this.id2Name.put(tableId, name);
  }

  public DBFile getDbFile(Integer tableId) {
    return id2File.get(tableId);
  }

  public TupleDesc getTupleDesc(int tableId) throws NoSuchElementException {
    return id2File.get(tableId).getTupleDesc();
  }
}
