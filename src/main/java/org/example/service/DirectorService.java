package org.example.service;

import org.example.models.Director;
import org.example.models.enums.Department;

import java.util.List;

public interface DirectorService {
    List<Director> getAllDirectors();
    Director getDirectorById(int id);
    Director getDirectorByDepartment(int id);
    void saveDirector(Director director);
    void updateDirector(int id, Director director);

    void deleteDirector(int id);
    int getNewId();
    boolean isDirectorOfDepartmentPresent(Department department);

}
