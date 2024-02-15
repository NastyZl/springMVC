package org.example.repository;

import org.example.models.Director;
import org.example.models.enums.Department;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public interface DirectorRepository {

    public List<Director> findAll();
    public Director findById(int id);
    public void save(Director director);

    public void update(int id, Director director);
    public void delete(int id);
}
