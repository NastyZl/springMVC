package org.example.service;

import org.example.models.Director;
import org.example.models.Employee;
import org.example.models.enums.Department;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<Employee> getAllEmployee();
    Employee getEmployeeById(int id);
    void saveEmployee(Employee employee);
    void setDirectorId(int employeeId, int directorId);
    void removeDirectorId(int employeeId);
    void updateEmployee(int id, Employee employee);
    void deleteEmployee(int id);
    int getNewId();
}
