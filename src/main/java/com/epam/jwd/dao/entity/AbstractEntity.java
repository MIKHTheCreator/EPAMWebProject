package com.epam.jwd.dao.entity;

import java.util.Objects;

public class AbstractEntity<T> {

    private T id;

    public AbstractEntity() {
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

}
