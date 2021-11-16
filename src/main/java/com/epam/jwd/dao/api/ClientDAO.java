package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.exception.DAOException;

import java.util.List;

public interface ClientDAO<T extends AbstractEntity<V>, V> {

    T save(T entity) throws DAOException;

    List<T> findAll() throws DAOException;

    T findById(V id) throws DAOException;

    T update(T entity) throws DAOException;

    void delete(T entity) throws DAOException;

    T findByUsername(String username) throws DAOException;
}
