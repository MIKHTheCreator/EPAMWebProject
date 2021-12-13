package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractEntityDTO;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

/**
 * @param <T> User DTO entity {@link com.epam.jwd.service.dto.user_account.UserDTO}
 * @param <V> type of id field
 * @author mikh
 * <p>
 * Interface with service representation of User DAO methods
 */
public interface UserService<T extends AbstractEntityDTO<V>, V> {

    /**
     * Method for saving DTO object to DB
     *
     * @param entity User DTO entity to save
     * @return Saved UserDTO
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T save(T entity) throws ServiceException;

    /**
     * Method for extracting all UserDTOs from DB
     *
     * @return List of UserDTOs
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findAll() throws ServiceException;

    /**
     * Method for getting UserDTO object by id from DB
     *
     * @param id UserDTO's id
     * @return UserDTO with provided id
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T findById(V id) throws ServiceException;

    /**
     * Method for updating UserDTO entity in DB
     *
     * @param entity UserDTO entity to update
     * @return Updated UserDTO entity
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T update(T entity) throws ServiceException;

    /**
     * Method for deleting UserDTO entity from DB
     *
     * @param entity UserDTO entity to delete
     * @throws ServiceException if any DAOExceptions were thrown
     */
    void delete(T entity) throws ServiceException;

    /**
     * Method for getting UserDTO from DB by Client's id
     *
     * @param id Client's id
     * @return founded ClientDTO entity
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T findUserByClientId(V id) throws ServiceException;

    /**
     * Method for update UserDto's passport id
     *
     * @param entity UserDTO with changed passport id field
     * @throws ServiceException if any DAOExceptions were thrown
     */
    void updateUserPassport(T entity) throws ServiceException;

    /**
     * Method for getting all UserDTOs on current Page with Page limit
     *
     * @param page       current page
     * @param numOfUsers num of UserDTOs on page
     * @return List of founded UserDTOs
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findUsersToPage(int page, int numOfUsers) throws ServiceException;
}
