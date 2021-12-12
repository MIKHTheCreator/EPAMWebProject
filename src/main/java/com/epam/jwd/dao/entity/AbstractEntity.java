package com.epam.jwd.dao.entity;

/**
 * @author mikh
 * Class which AbstractEntity which provides id of provided type
 * @param <T> type of id
 */
public class AbstractEntity<T> {

    /**
     * Field of generic type which provides id
     */
    private T id;

    /**
     * Constructor to create Object of current class
     * @see AbstractEntity#AbstractEntity(Object)
     */
    public AbstractEntity() {
    }

    /**
     * Constructor which creates object with initialized id field {@link AbstractEntity#id}
     * @param id id to initialize
     */
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
