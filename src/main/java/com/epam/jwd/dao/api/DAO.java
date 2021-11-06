package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.exception.DAOException;

import java.util.List;

public interface DAO<T extends AbstractEntity<K>, K> {

    T save(T entity) throws DAOException;

    List<T> findAll() throws DAOException;

    T findById(K id) throws DAOException;

    T update(T entity) throws DAOException;

    void delete(T entity) throws DAOException;

}
