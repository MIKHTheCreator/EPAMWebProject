package com.epam.jwd.service.dto;

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
