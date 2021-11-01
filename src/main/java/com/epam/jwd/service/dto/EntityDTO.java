package com.epam.jwd.service.dto;

public class EntityDTO<T> {

    private T id;

    public EntityDTO() {
    }

    public EntityDTO(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
