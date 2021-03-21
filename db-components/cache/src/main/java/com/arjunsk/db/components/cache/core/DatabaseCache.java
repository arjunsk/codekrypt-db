package com.arjunsk.db.components.cache.core;

import com.arjunsk.db.components.cache.domain.Employee;
import com.arjunsk.db.components.cache.support.MockDatabase;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import java.util.concurrent.TimeUnit;

public class DatabaseCache {

  private final LoadingCache<String, Employee> cache;

  public DatabaseCache() {
    // create a cache for employees based on their employee id
    cache =
        CacheBuilder.newBuilder()
            .maximumSize(100) // maximum 100 records can be cached
            .expireAfterAccess(30, TimeUnit.MINUTES) // cache will expire after 30 minutes of access
            .removalListener(
                (RemovalListener<String, Employee>)
                    removalNotification ->
                        System.out.println(
                            removalNotification.getValue().getEmployeeId() + " removed"))
            .build(
                new CacheLoader<String, Employee>() {
                  @Override
                  public Employee load(String employeeId) {
                    return MockDatabase.getFromDatabase(employeeId);
                  }
                });
  }

  public LoadingCache<String, Employee> getCache() {
    return cache;
  }

  public void evict(String employeeId) {
    cache.invalidate(employeeId);
  }

  public void close() {
    cache.invalidateAll();
  }
}
