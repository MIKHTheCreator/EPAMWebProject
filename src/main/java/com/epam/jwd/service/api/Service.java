package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.EntityDTO;

import java.util.List;

public interface Service<T extends EntityDTO<V>, V> {

    T save(T entity);
    List<T> findAll();
    T findById(V id);
    T update(T entity);
    void delete(T entity);
}
