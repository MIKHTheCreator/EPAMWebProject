package com.epam.jwd.service.impl.payment_system;

import com.epam.jwd.dao.api.CreditCardDAO;
import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.entity.payment_system.CreditCard;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.impl.CreditCardDAOImpl;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.mapper.payment_system.CreditCardDTOMapper;
import com.epam.jwd.service.dto.payment_system.CreditCardDTO;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_DELETE_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_DELETE_METHOD_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_FIND_ALL_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_FIND_BY_ID_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_SAVE_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_SAVE_METHOD_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_UPDATE_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_UPDATE_METHOD_EXCEPTION_CODE;

public class CreditCardService implements com.epam.jwd.service.api.CreditCardService<CreditCardDTO, Integer> {

    private final CreditCardDAO<CreditCard, Integer> creditCardDAO;
    private final DTOMapper<CreditCardDTO, CreditCard, Integer> mapper;

    private static final Logger log = LogManager.getLogger(CreditCardService.class);

    public CreditCardService() {
        this.creditCardDAO = CreditCardDAOImpl.getInstance();
        this.mapper = new CreditCardDTOMapper();
    }

    @Override
    public CreditCardDTO save(CreditCardDTO creditCardDTO) throws ServiceException {
        CreditCard creditCard = mapper.convertToEntity(creditCardDTO);

        try {
            creditCardDTO = mapper.convertToDTO(creditCardDAO.save(creditCard));
        } catch (DAOException e) {
            log.error(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
        }

        return creditCardDTO;
    }

    @Override
    public List<CreditCardDTO> findAll() throws ServiceException {
        List<CreditCardDTO> creditCards = new ArrayList<>();

        try {
            for (CreditCard creditCard : creditCardDAO.findAll()) {
                creditCards.add(mapper.convertToDTO(creditCard));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return creditCards;
    }

    @Override
    public CreditCardDTO findById(Integer id) throws ServiceException {
        CreditCardDTO creditCardDTO;

        try {
            creditCardDTO = mapper.convertToDTO(creditCardDAO.findById(id));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return creditCardDTO;
    }

    @Override
    public CreditCardDTO update(CreditCardDTO creditCardDTO) throws ServiceException {
        CreditCard creditCard = mapper.convertToEntity(creditCardDTO);

        try {
            creditCardDTO = mapper.convertToDTO(creditCardDAO.update(creditCard));
        } catch (DAOException e) {
            log.error(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
        }

        return creditCardDTO;
    }

    @Override
    public void delete(CreditCardDTO creditCardDTO) throws ServiceException {
        CreditCard creditCard = mapper.convertToEntity(creditCardDTO);

        try {
            creditCardDAO.delete(creditCard);
        } catch (DAOException e) {
            log.error(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
        }
    }

    @Override
    public List<CreditCardDTO> findCreditCardsByUserId(Integer id) throws ServiceException {
        List<CreditCardDTO> creditCards = new ArrayList<>();

        try {
            for (CreditCard creditCard : creditCardDAO.findCreditCardsByUserId(id)) {
                creditCards.add(mapper.convertToDTO(creditCard));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return creditCards;
    }

    @Override
    public CreditCardDTO findCreditCardByBankAccountId(Integer id) throws ServiceException {
        CreditCardDTO creditCardDTO;

        try {
            creditCardDTO = mapper.convertToDTO(creditCardDAO.findCreditCardByBankAccountId(id));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return creditCardDTO;
    }
}
