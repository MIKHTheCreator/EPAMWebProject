package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractEntityDTO;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

/**
 * @param <T> Payment DTO entity {@link com.epam.jwd.service.dto.payment_system.PaymentDTO}
 * @param <V> type of id field
 * @author mikh
 * <p>
 * Interface with service representation of Payment DAO methods
 */
public interface PaymentService<T extends AbstractEntityDTO<V>, V> {

    /**
     * Method for saving DTO object to DB
     *
     * @param entity Payment DTO entity to save
     * @return Saved PaymentDTO
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T save(T entity) throws ServiceException;

    /**
     * Method for extracting all PaymentDTOs from DB
     *
     * @return List of PaymentDTOs
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findAll() throws ServiceException;

    /**
     * Method for getting PaymentDTO object by id from DB
     *
     * @param id PaymentDTO's id
     * @return PaymentDTO with provided id
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T findById(V id) throws ServiceException;

    /**
     * Method for updating PaymentDTO entity in DB
     *
     * @param entity PaymentDTO entity to update
     * @return Updated PaymentDTO entity
     * @throws ServiceException if any DAOExceptions were thrown
     */
    T update(T entity) throws ServiceException;

    /**
     * Method for deleting PaymentDTO entity from DB
     *
     * @param entity PaymentDTO entity to delete
     * @throws ServiceException if any DAOExceptions were thrown
     */
    void delete(T entity) throws ServiceException;

    /**
     * Method for getting List of PaymentDTOs by provided UserId
     *
     * @param id User's id
     * @return List of founded PaymentDTOs
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findPaymentsByUserId(V id) throws ServiceException;

    /**
     * Method for getting payments by User's id and set page limit
     *
     * @param id            User's id
     * @param page          page limit
     * @param numOfPayments (amount of payments on page)
     * @return List of founded PaymentDTOs
     * @throws ServiceException if any DAOExceptions were thrown
     */
    List<T> findPaymentsByUserIdAndPage(V id, int page, int numOfPayments) throws ServiceException;
}
