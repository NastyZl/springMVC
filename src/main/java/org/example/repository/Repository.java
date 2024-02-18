package org.example.repository;

import org.example.models.Director;

import java.util.List;
import java.util.Optional;


public interface Repository<T>{

    public List<T> findAll();
    public Optional<T> findById(int id);
    public void save(T item);

    public void update(int id, T item);
    public void delete(int id);
    public int count();
}
