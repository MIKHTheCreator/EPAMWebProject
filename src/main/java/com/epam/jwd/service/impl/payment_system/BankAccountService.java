package com.epam.jwd.service.impl.payment_system;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.entity.payment_system.BankAccount;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.impl.BankAccountDAOImpl;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.mapper.payment_system.BankAccountDTOMapper;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
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

public class BankAccountService implements Service<BankAccountDTO, Integer> {

    private final DAO<BankAccount, Integer> bankAccountDAO;
    private final DTOMapper<BankAccountDTO, BankAccount, Integer> mapper;

    private static final Logger log = LogManager.getLogger(BankAccountService.class);

    public BankAccountService() {
        this.bankAccountDAO = BankAccountDAOImpl.getInstance();
        this.mapper = new BankAccountDTOMapper();
    }

    @Override
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) throws ServiceException {
        BankAccount bankAccount = mapper.convertToEntity(bankAccountDTO);

        try {
            bankAccountDTO = mapper.convertToDTO(bankAccountDAO.save(bankAccount));
        } catch (DAOException e) {
            log.error(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
        }

        return bankAccountDTO;
    }

    @Override
    public List<BankAccountDTO> findAll() throws ServiceException {
        List<BankAccountDTO> bankAccounts = new ArrayList<>();

        try {
            for(BankAccount bankAccount : bankAccountDAO.findAll()) {
                bankAccounts.add(mapper.convertToDTO(bankAccount));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return bankAccounts;
    }

    @Override
    public BankAccountDTO findById(Integer id) throws ServiceException {
        BankAccountDTO bankAccountDTO;

        try {
            bankAccountDTO = mapper.convertToDTO(bankAccountDAO.findById(id));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return bankAccountDTO;
    }

    @Override
    public BankAccountDTO update(BankAccountDTO bankAccountDTO) throws ServiceException {
        BankAccount bankAccount = mapper.convertToEntity(bankAccountDTO);

        try {
            bankAccountDTO = mapper.convertToDTO(bankAccountDAO.update(bankAccount));
        } catch (DAOException e) {
            log.error(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
        }

        return bankAccountDTO;
    }

    @Override
    public void delete(BankAccountDTO bankAccountDTO) throws ServiceException {
        BankAccount bankAccount = mapper.convertToEntity(bankAccountDTO);

        try {
            bankAccountDAO.delete(bankAccount);
        } catch (DAOException e) {
            log.error(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
        }
    }
}
