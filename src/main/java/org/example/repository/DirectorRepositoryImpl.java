package org.example.repository;

import org.example.models.Director;
import org.example.models.enums.Department;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DirectorRepositoryImpl  implements DirectorRepository{

    private final List<Director> directors;

    public DirectorRepositoryImpl(List<Director> directors) {
        this.directors = directors;
    }

    @Override
    public List<Director> findAll() {
        return directors;
    }

    @Override
    public Director findById(int id) {
        return directors.stream().filter(director -> director.getId()==id).findFirst().orElse(null);
    }

    public void save(Director director) {
        director.setId(getLastId());
        directors.add(director);
    }

    public int getLastId(){
        if (directors.isEmpty()){
            return 1;
        }
        return directors.get(directors.size()-1).getId()+1;
    }

    @Override
    public void update(int id, Director director) {
        Director directorToBeUpdated = findById(id);
        directorToBeUpdated.setName(director.getName());
        directorToBeUpdated.setDepartment(director.getDepartment());
        directorToBeUpdated.setSubordinateEmployees(director.getSubordinateEmployees());
    }

    @Override
    public void delete(int id) {
        directors.removeIf(director -> director.getId() == id);
    }
}
