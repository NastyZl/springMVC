package org.example.repository;

import java.util.List;
import java.util.Optional;


public interface Repository<T> {

    List<T> findAll();

    Optional<T> findById(int id);

    void save(T item);

    void update(int id, T item);

    void delete(int id);

    int count();
}
