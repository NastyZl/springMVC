package org.example.service;

import org.example.models.Director;
import org.example.models.enums.Department;
import org.example.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final Repository<Director> directorRepository;

    public DirectorServiceImpl(Repository<Director> directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    @Override
    public Director getDirectorById(int id) {
        return directorRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Director with id=" + id + " don't exist"));
    }

    @Override
    public Director getDirectorByDepartment(int id) {
        return null;
    }

    @Override
    public void saveDirector(Director director) {
        directorRepository.save(director);
    }

    @Override
    public void updateDirector(int id, Director director) {
        Optional<Director> optionalDirectorRepository = directorRepository.findById(id);
        Optional<Director> optionalDirectorToUpdate = Optional.ofNullable(director);
        if (optionalDirectorRepository.isPresent() && optionalDirectorToUpdate.isPresent()) {
            directorRepository.update(id, optionalDirectorToUpdate.get());
        } else {
            throw new RuntimeException("UPDATE");
        }
    }

    @Override
    public void deleteDirector(int id) {
        this.directorRepository.delete(id);
    }

    @Override
    public int getNewId() {
        return directorRepository.count()+1;
    }

    @Override
    public boolean isDirectorOfDepartmentPresent(Department department) {
        return directorRepository.findAll().stream().anyMatch(director -> director.getDepartment() == department);
    }
}
