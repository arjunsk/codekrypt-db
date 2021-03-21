package com.arjunsk.db.components.cache.support.domain;

import com.google.common.base.MoreObjects;

public class Employee {
  private String name;
  private String dept;
  private String employeeId;

  public Employee(String name, String dept, String empID) {
    this.name = name;
    this.dept = dept;
    this.employeeId = empID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDept() {
    return dept;
  }

  public void setDept(String dept) {
    this.dept = dept;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(Employee.class)
        .add("Name", name)
        .add("Department", dept)
        .add("Emp Id", employeeId)
        .toString();
  }
}
