package com.arjunsk.db.components.cache;

import com.arjunsk.db.components.cache.core.DatabaseCache;
import java.util.concurrent.ExecutionException;

public class CacheDriver {
  public static void main(String args[]) {

    DatabaseCache databaseCache = new DatabaseCache();

    try {
      // on first invocation, cache will be populated with corresponding employee record
      System.out.println("Invocation #1");
      System.out.println(databaseCache.getCache().get("100"));
      System.out.println(databaseCache.getCache().get("103"));
      System.out.println(databaseCache.getCache().get("110"));

      // second invocation, data will be returned from cache
      System.out.println("Invocation #2");
      System.out.println(databaseCache.getCache().get("100"));
      System.out.println(databaseCache.getCache().get("103"));
      System.out.println(databaseCache.getCache().get("110"));

    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    // When closing
    databaseCache.close();
  }
}
