package org.example.service;

import org.example.exception.CustomException;
import org.example.models.Director;
import org.example.models.enums.Department;

import java.util.List;
import java.util.Map;

public interface DirectorService {
    List<Director> getAllDirectors();

    Director getDirectorById(int id) throws CustomException;

    void saveDirector(Director director);

    void updateDirector(int id, Director director) throws CustomException;

    void deleteDirector(int id);

    int getNewId();

    boolean isDirectorOfDepartmentPresent(Department department);

    Map<Integer, Department> getDirectorDepartmentMap();

}
