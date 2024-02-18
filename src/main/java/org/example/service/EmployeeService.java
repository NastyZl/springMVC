package org.example.service;

import org.example.models.Director;
import org.example.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployee();
    Employee getEmployeeById(int id);
    void saveEmployee(Employee employee);
    void setDirectorId(int employeeId, int directorId);
    void removeDirectorId(int employeeId);
}
