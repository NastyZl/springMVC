package org.example.service;

import org.example.models.Employee;
import org.example.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final Repository<Employee> employeeRepository;

    public EmployeeServiceImpl( Repository<Employee> employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {

        employeeRepository.save(employee);

    }

}
