package org.example.repository;


import org.example.models.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final List<Employee> employees;

    public EmployeeRepositoryImpl(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee findById(int id) {
        return employees.stream().filter(employees -> employees.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(Employee employee) {
        employee.setId(getLastId());
        employees.add(employee);
    }

    public int getLastId() {
        if (employees.isEmpty()) {
            return 1;
        }
        return employees.get(employees.size() - 1).getId() + 1;
    }

    @Override
    public void update(int id, Employee employee) {
        Employee employeeToBeUpdated = findById(id);
        employeeToBeUpdated.setName(employee.getName());
        employeeToBeUpdated.setPost(employee.getPost());
    }

    @Override
    public void delete(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }
}
