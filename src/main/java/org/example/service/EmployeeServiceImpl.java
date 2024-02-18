package org.example.service;

import org.example.models.Director;
import org.example.models.Employee;
import org.example.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final Repository<Director> directorRepository;
    private final Repository<Employee> employeeRepository;

    public EmployeeServiceImpl(Repository<Director> directorRepository, Repository<Employee> employeeRepository) {
        this.directorRepository = directorRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Director with id=" + id + " don't exist"));

    }

    @Override
    public void saveEmployee(Employee employee) {

        employeeRepository.save(employee);

    }

    @Override
    public void setDirectorId(int employeeId, int directorId) {
    //    Director director = directorRepository.findById(directorId).orElseThrow(() -> new IllegalArgumentException("такого директора нет"));
        employeeRepository.findById(employeeId).ifPresent((employee -> {
            employee.setIdDirector(directorId);
        }));

    }

    @Override
    public void removeDirectorId(int employeeId) {
        employeeRepository.findById(employeeId).ifPresent((employee -> {
            employee.setIdDirector(0);
        }));
    }

}
