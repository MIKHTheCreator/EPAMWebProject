package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractEntityDTO;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

public interface Service<T extends AbstractEntityDTO<V>, V> {

    T save(T entity) throws ServiceException;
    List<T> findAll() throws ServiceException;
    T findById(V id) throws ServiceException;
    T update(T entity) throws ServiceException;
    void delete(T entity) throws ServiceException;
}
