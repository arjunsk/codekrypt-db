package com.arjunsk.db.components.cache.support;

import com.arjunsk.db.components.cache.support.domain.Employee;
import java.util.HashMap;
import java.util.Map;

public class MockDatabase {

  public static Employee getFromDatabase(String empId) {

    Employee e1 = new Employee("Mahesh", "Finance", "100");
    Employee e2 = new Employee("Rohan", "IT", "103");
    Employee e3 = new Employee("Sohan", "Admin", "110");

    Map<String, Employee> database = new HashMap<String, Employee>();

    database.put("100", e1);
    database.put("103", e2);
    database.put("110", e3);

    System.out.println("Database hit for" + empId);

    return database.get(empId);
  }
}
