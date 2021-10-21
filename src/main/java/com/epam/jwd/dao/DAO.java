package com.epam.jwd.dao;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.util.List;

public interface DAO<T extends AbstractEntity<K>, K> {

    T save(T entity) throws InterruptedException;
    List<T> findAll() throws InterruptedException;
    T findById(K id) throws InterruptedException;
    T update(T entity) throws InterruptedException;
    void delete(T entity) throws InterruptedException;

}
