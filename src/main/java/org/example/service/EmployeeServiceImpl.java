package org.example.service;

import org.example.models.Director;
import org.example.models.Employee;
import org.example.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Repository<Employee> employeeRepository;
    private final Repository<Director> directorRepository;

    public EmployeeServiceImpl(Repository<Employee> employeeRepository, Repository<Director> directorRepository) {
        this.employeeRepository = employeeRepository;
        this.directorRepository = directorRepository;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with id=" + id + " don't exist"));
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void setDirectorId(int employeeId, int directorId) {
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

    @Override
    public void updateEmployee(int id, Employee employee) {
        Optional<Employee> optionalEmployeeRepository = employeeRepository.findById(id);
        Optional<Employee> optionalEmployeeToUpdate = Optional.ofNullable(employee);
        if (optionalEmployeeRepository.isPresent() && optionalEmployeeToUpdate.isPresent()) {
            employeeRepository.update(id, optionalEmployeeToUpdate.get());
        } else {
            throw new RuntimeException("UPDATE");
        }
    }

    @Override
    public void deleteEmployee(int id) {

    }

//    @Override
//    public void deleteEmployee(int id) {
//        Optional<Employee> employee = employeeRepository.findById(id);
//        employee.ifPresent(e -> {
//            Optional<Director> director = directorRepository.findById(e.getIdDirector());
//            director.ifPresent(d -> d.);
//        });
//        this.employeeRepository.delete(id);
//    }

    @Override
    public int getNewId() {
        return 0;
    }

}
