package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractEntityDTO;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

/**
 * @param <T> Client DTO entity {@link com.epam.jwd.service.dto.payment_system.CreditCardDTO}
 * @param <V> type of id field
 * @author mikh
 * <p>
 * Interface with service representation of CreditCard DAO methods
 */
public interface CreditCardService<T extends AbstractEntityDTO<V>, V> {

    /**
     * Method for saving DTO object to DB
     *
     * @param entity DTO Credit Card DTO entity to save
     * @return Saved CreditCardDTO
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T save(T entity) throws ServiceException;

    /**
     * Method for extracting all CreditCardDTOs from DB
     *
     * @return List of CreditCardDtos
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findAll() throws ServiceException;

    /**
     * Method for getting CreditCard object by id from DB
     *
     * @param id CreditCardDTO's id
     * @return CreditCardDTO with provided id
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T findById(V id) throws ServiceException;

    /**
     * Method for updating CreditCardDTO entity in DB
     *
     * @param entity CreditCardDTO entity to update
     * @return Updated CreditCardDTO entity
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T update(T entity) throws ServiceException;

    /**
     * Method for deleting CreditCardDTO entity from DB
     *
     * @param entity CreditCardDTO entity to delete
     * @throws ServiceException if any DAOExceptions were thrown
     */
    void delete(T entity) throws ServiceException;

    /**
     * Method for getting LIst of CreditCardDTOs by User id
     *
     * @param id User's id
     * @return List of CreditCardDTOs
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findCreditCardsByUserId(V id) throws ServiceException;

    /**
     * Method for getting CreditCardDTO entity from DB by BankAccountId
     *
     * @param id Bank account id
     * @return Founded CreditCardDTO entity
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T findCreditCardByBankAccountId(V id) throws ServiceException;
}
