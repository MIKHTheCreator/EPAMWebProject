package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.exception.DAOException;

import java.util.List;

public interface DAO<T extends AbstractEntity<K>, K> {

    T save(T entity) throws InterruptedException, DAOException;
    List<T> findAll() throws InterruptedException, DAOException;
    T findById(K id) throws InterruptedException;
    T update(T entity) throws InterruptedException;
    void delete(T entity) throws InterruptedException;

}
