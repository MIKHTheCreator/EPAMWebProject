package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.exception.DAOException;

import java.util.List;

/**
 * @param <T> the entity that extends AbstractEntity class {@link AbstractEntity}
 * @param <K> the type of entity id field provided from AbstractEntity class {@link AbstractEntity#id}
 * @author mikh
 * This interface is creted due to DAO Pattern and provide user with CRUD methods and an extra
 * options to find payments by user id and find payments by user id with page sort
 */
public interface PaymentDAO<T extends AbstractEntity<K>, K> {

    /**
     * Save method which provide user with opportunity to save entity to the DataBase
     *
     * @param entity entity to save in DataBase
     * @return the saved entity value with generated by DataBase id
     * @throws DAOException if the connection can't be accepted or save operation
     *                      was failed
     */
    T save(T entity) throws DAOException;

    /**
     * Method which extracts all values from DataBase and put it to the List {@link List}
     *
     * @return list of entities in current DataBase table
     * @throws DAOException if the connection can't be accepted or findAll operation
     *                      was failed
     */
    List<T> findAll() throws DAOException;

    /**
     * Method which extracts entity from DataBase with provided id of type {@link AbstractEntity#id}
     *
     * @param id id of provided type to find entity in DataBase
     * @return entity of current type
     * @throws DAOException if the connection can't be accepted or findById operation
     *                      was failed
     */
    T findById(K id) throws DAOException;

    /**
     * Method which updates some entity fields in DataBase by provided entity
     *
     * @param entity provided entity with updated fields
     * @return updated entity
     * @throws DAOException if the connection can't be accepted or update operation
     *                      was failed
     */
    T update(T entity) throws DAOException;

    /**
     * Method which deletes current entity from Database by provided entity
     *
     * @param entity entity to delete
     * @throws DAOException if the connection can't be accepted or delete operation
     *                      was failed
     */
    void delete(T entity) throws DAOException;

    /**
     * Method which extracts List {@link List} of entities from DataBase with provided id
     *
     * @param id id field to find for
     * @return List of entities with provided id
     * @throws DAOException if the connection can't be accepted or findAllPaymentsByUserId operation
     *                      was failed
     */
    List<T> findAllPaymentsByUserId(K id) throws DAOException;

    /**
     * Method which extracts List {@link List} of entities from DataBase with set limit
     *
     * @param id            id field to find for
     * @param page          current page number
     * @param numOfPayments max limit of entities on ine page
     * @return List of entities with provided id in amount of numOfPayments argument
     * @throws DAOException if the connection can't be accepted or findPaymentsByUserIdAndPageLimit operation
     *                      was failed
     */
    List<T> findPaymentsByUserIdAndPageLimit(K id, int page, int numOfPayments) throws DAOException;

}