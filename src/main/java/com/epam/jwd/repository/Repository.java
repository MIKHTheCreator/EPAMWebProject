package com.epam.jwd.repository;

import com.epam.jwd.repository.entity.AbstractEntity;

import java.util.List;

public interface Repository<T extends AbstractEntity<K>, K> {

    T save(T entity) throws InterruptedException;
    List<T> findAll();
    T findById(K id);
    T update(T entity);
    void delete(T entity);

}
