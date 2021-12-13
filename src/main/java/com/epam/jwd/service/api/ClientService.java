package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractEntityDTO;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

/**
 * @param <T> Client DTO entity {@link com.epam.jwd.service.dto.user_account.ClientDTO}
 * @param <V> type of id field
 * @author mikh
 * <p>
 * Interface with service representation of Client DAO methods
 */
public interface ClientService<T extends AbstractEntityDTO<V>, V> {

    /**
     * Method for saving DTO object to DB
     *
     * @param entity DTO Client entity to save
     * @return Saved ClientDTO
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T save(T entity) throws ServiceException;

    /**
     * Method for extracting all ClientDTOs from DB
     *
     * @return List of ClientDTOs
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findAll() throws ServiceException;

    /**
     * Method for getting ClientDto object by id from DB
     *
     * @param id ClientDTO's id
     * @return ClientDTO with provided id
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T findById(V id) throws ServiceException;

    /**
     * Method for updating ClientDTO entity in DB
     *
     * @param entity ClientDTO entity to update
     * @return Updated ClientDTO entity
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T update(T entity) throws ServiceException;

    /**
     * Method for deleting ClientDTO entity from DB
     *
     * @param entity ClientDTO entity to delete
     * @throws ServiceException if any DAOExceptions were thrown
     */
    void delete(T entity) throws ServiceException;

    /**
     * Method for extracting ClientDTO entity by username
     *
     * @param username Client's username
     * @return Founded ClientDTO
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T findClientByUsername(String username) throws ServiceException;
}
