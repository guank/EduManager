package com.example.lunar.edumanager;

public class Employee {
    private String employeeId;
    private String employeeName;
    private String employeeLname;

    public Employee(){

    }

    public Employee(String employeeId, String employeeName, String employeeLname) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeLname = employeeLname;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeLname() {
        return employeeLname;
    }
}
