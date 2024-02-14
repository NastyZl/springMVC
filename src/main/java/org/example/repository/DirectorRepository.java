package org.example.repository;

import org.example.model.Director;
import org.example.model.enums.Department;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DirectorRepository {
    private static int ID;
    private List<Director> directors;

    {
        directors = new ArrayList<>();

        directors.add(new Director(++ID, "IVAN", Department.DEVELOPMENT));
        directors.add(new Director(++ID, "PETR", Department.MANAGEMENT));
    }

    public List<Director> index() {
        return directors;
    }

    public Director show(int id) {
        return directors.stream().filter(director -> director.getId() == id).findFirst().orElse(null);
    }

    public Director showDirectorOfDepartment(int id) {
        return directors.stream().filter(director -> director.getDepartment().getId() == id).findFirst().orElse(null);
    }

    public void save(Director director) {
        director.setId(++ID);
        directors.add(director);
    }

    public void update(int id, Director director) {
        Director directorToBeUpdated = show(id);
        directorToBeUpdated.setName(director.getName());
        directorToBeUpdated.setDepartment(director.getDepartment());
    }
    public void delete(int id) {
        directors.removeIf(director -> director.getId() == id);
    }
    public boolean existByDepartment(Department department) {
        return directors.stream().anyMatch(director -> director.getDepartment() == department);
    }
}
