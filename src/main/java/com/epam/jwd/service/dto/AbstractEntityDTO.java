package com.epam.jwd.service.dto;

/**
 * @author mikh
 * AbstractEntityDTO class for provideing id field
 * @see com.epam.jwd.dao.entity.AbstractEntity
 */
public class AbstractEntityDTO<T> {

    private T id;

    public AbstractEntityDTO() {
    }

    public AbstractEntityDTO(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
