package com.arjunsk.db.components.concurrent.ds;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentHashMapDriver {

  public static void main(String[] args) {

    // 1. Useful functions
    ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
    map.put("foo", "bar");
    map.put("han", "solo");
    map.put("r2", "d2");
    map.put("c3", "p0");

    map.computeIfPresent("foo", (key, value) -> value + value);
    System.out.println(map.get("foo")); // barbar

    map.merge("foo", "boo", (oldVal, newVal) -> newVal + " was " + oldVal);
    System.out.println(map.get("foo")); // boo was foo

    // 2. Parallelism within ConcurrentHashMap
    System.out.println(ForkJoinPool.getCommonPoolParallelism()); // 3

    ConcurrentHashMap<String, String> map2 = new ConcurrentHashMap<>();
    map2.put("foo", "bar");
    map2.put("han", "solo");
    map2.put("r2", "d2");
    map2.put("c3", "p0");

    // NOTE 1: BiConsumer is (Key, Value)

    // NOTE 2: All of those methods use a common first argument called parallelismThreshold. This
    // threshold indicates the minimum collection size when the operation should be executed in
    // parallel. E.g. if you pass a threshold of 500 and the actual size of the map is 499 the
    // operation will be performed sequentially on a single thread. In the next examples we use a
    // threshold of one to always force parallel execution for demonstrating purposes.

    map2.forEach(
        1,
        (key, value) ->
            System.out.printf(
                "key: %s; value: %s; thread: %s\n", key, value, Thread.currentThread().getName()));
  }
}
