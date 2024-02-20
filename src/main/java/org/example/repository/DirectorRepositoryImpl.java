package org.example.repository;

import org.example.models.Director;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class DirectorRepositoryImpl implements Repository<Director> {
    private final List<Director> directors;

    private int COUNT_DIRECTORS;

    @PostConstruct
    public void postConstruct() {
        COUNT_DIRECTORS = directors.size();
    }

    public DirectorRepositoryImpl(List<Director> directors) {
        this.directors = directors;
    }

    @Override
    public List<Director> findAll() {
        return directors;
    }

    @Override
    public Optional<Director> findById(int id) {
        return directors.stream().filter(director -> director.getId() == id).findFirst();
    }

    public void save(Director director) {
        director.setId(++COUNT_DIRECTORS);
        directors.add(director);
    }

    @Override
    public void update(int id, Director director) {
        findById(id).ifPresent(directorToBeUpdated -> {
            directorToBeUpdated.setName(director.getName());
            directorToBeUpdated.setDepartment(director.getDepartment());
        });
    }

    @Override
    public void delete(int id) {
        directors.removeIf(director -> director.getId() == id);
    }

    @Override
    public int count() {
        return COUNT_DIRECTORS;
    }
}
