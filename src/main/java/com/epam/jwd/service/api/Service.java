package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractEntityDTO;

import java.util.List;

public interface Service<T extends AbstractEntityDTO<V>, V> {

    T save(T entity);
    List<T> findAll();
    T findById(V id);
    T update(T entity);
    void delete(T entity);
}
