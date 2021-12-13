package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractEntityDTO;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

/**
 * @param <T> Service abstract DTO entity
 * @param <V> type of id field
 * @author mikh
 * <p>
 * Interface with service representation of DAO methods
 */
public interface Service<T extends AbstractEntityDTO<V>, V> {

    /**
     * Method for saving DTO object to DB
     *
     * @param entity DTO entity to save
     * @return Saved entity
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T save(T entity) throws ServiceException;

    /**
     * Method for extracting all entities from DB
     *
     * @return List of entities
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findAll() throws ServiceException;

    /**
     * Method for getting entity object by id from DB
     *
     * @param id entity's id
     * @return entity with provided id
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T findById(V id) throws ServiceException;

    /**
     * Method for updating entity in DB
     *
     * @param entity entity to update
     * @return Updated entity
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T update(T entity) throws ServiceException;

    /**
     * Method for deleting entity from DB
     *
     * @param entity entity to delete
     * @throws ServiceException if any DAOExceptions were thrown
     */
    void delete(T entity) throws ServiceException;
}
