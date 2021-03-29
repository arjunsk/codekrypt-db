package com.arjunsk.db.components.cache.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheDemo {

  public static void main(String args[]) {

    LinkedHashMap<String, String> lruCache =
        new LinkedHashMap<String, String>(6, 0.75f, true) {

          @Override
          protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            // LRU Cache of size 4
            return size() > 4;
          }
        };

    lruCache.put("test", "test");
    lruCache.put("test1", "test1");
    lruCache.put("1", "abc");
    lruCache.put("test2", "test2");
    lruCache.put("1", "abc");
    lruCache.put("test3", "test3");
    lruCache.put("test4", "test4");
    lruCache.put("test3", "test3");
    lruCache.put("1", "abc");
    lruCache.put("test1", "test1");

    System.out.println(lruCache);
  }
}
