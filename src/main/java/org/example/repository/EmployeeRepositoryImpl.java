package org.example.repository;


import org.example.models.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeRepositoryImpl implements Repository<Employee> {
    private final List<Employee> employees;

    public EmployeeRepositoryImpl(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Optional<Employee> findById(int id) {
        return employees.stream().filter(employees -> employees.getId() == id).findFirst();
    }

    @Override
    public void save(Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
    }

    @Override
    public void update(int id, Employee employee) {
        findById(id).ifPresent(employeeToBeUpdated -> {
            employeeToBeUpdated.setName(employee.getName());
            employeeToBeUpdated.setPost(employee.getPost());
        });
    }

    @Override
    public void delete(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }

    @Override
    public int count() {
        return 0;
    }
}
