package org.example.models;


import org.example.models.enums.Department;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Director {
    private int id;
    @NotEmpty(message = "fill in the NAME field")
    @Size(min = 2, max = 30, message = "Name should be 2-30 chars")
    private String name;
    private Department department;
    @Size(min = 3, message = "There must be at least THREE subordinates")
    @NotEmpty(message = "subordinate Employees null")
    List<Employee> subordinateEmployees;

    public Director(int id, String name, Department department, List<Employee> subordinateEmployees) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.subordinateEmployees=subordinateEmployees;

    }

    public Director() {
    }

    @Override
    public String toString() {
        return "Director{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + department +
                ", subordinateEmployees=" + subordinateEmployees +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Employee> getSubordinateEmployees() {
        return subordinateEmployees;
    }

    public void setSubordinateEmployees(List<Employee> subordinateEmployees) {
        this.subordinateEmployees = subordinateEmployees;
    }
    public void addSubordinateEmployee(Employee employee) {
        if (this.subordinateEmployees == null) {
            this.subordinateEmployees = new ArrayList<>();
        }
        this.subordinateEmployees.add(employee);
    }

}
