package org.example.service;

import org.example.exception.CustomException;
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
    public Employee getEmployeeById(int id) throws CustomException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException("Employee with id=" + id + " don't exist"));
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void setDirectorId(int employeeId, int directorId) {
        employeeRepository.findById(employeeId).ifPresent((employee ->
            employee.setIdDirector(directorId)));
    }

    @Override
    public void removeDirectorId(int employeeId) {
        employeeRepository.findById(employeeId).ifPresent((employee -> employee.setIdDirector(0)));
    }

    @Override
    public void updateEmployee(int id, Employee employee) {
        Optional<Employee> employeeOld = employeeRepository.findById(id);
        Optional<Director> directorIdNew = directorRepository.findById(employee.getIdDirector());//id director new
        Optional<Director> directorIdOld = directorRepository.findById(employeeOld.get().getIdDirector());
        directorIdOld.ifPresent(director -> director.getSubordinateEmployees().removeIf(emp -> emp.getId() == id));
        directorIdNew.ifPresent(director -> director.addSubordinateEmployee(employee));
        employeeRepository.update(id, employee);
    }

    @Override
    public void deleteEmployee(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.ifPresent(emp -> {
            Optional<Director> director = directorRepository.findById(emp.getIdDirector());
            director.ifPresent(dir -> dir.getSubordinateEmployees().removeIf(e -> e.getId() == id));
        });
        this.employeeRepository.delete(id);
    }
}
