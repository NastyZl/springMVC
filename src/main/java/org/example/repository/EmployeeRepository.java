package org.example.repository;

import org.example.models.Director;
import org.example.models.Employee;
import org.example.models.enums.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public interface EmployeeRepository {
    public List<Employee> findAll();
    public Employee findById(int id);
    public void save(Employee employee);

    public void update(int id, Employee employee);
    public void delete(int id);
}
