package org.example.service;

import org.example.exception.CustomException;
import org.example.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    Employee getEmployeeById(int id) throws CustomException;

    void saveEmployee(Employee employee);

    void setDirectorId(int employeeId, int directorId);

    void removeDirectorId(int employeeId);

    void updateEmployee(int id, Employee employee);

    void deleteEmployee(int id);
}
