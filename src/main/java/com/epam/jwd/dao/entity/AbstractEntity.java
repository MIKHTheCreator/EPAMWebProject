package com.epam.jwd.dao.entity;


public class AbstractEntity<T> {

    private T id;

    public AbstractEntity() {
    }

    public AbstractEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

}
