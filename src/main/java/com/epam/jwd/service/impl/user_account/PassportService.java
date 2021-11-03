package com.epam.jwd.service.impl.user_account;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.entity.user_account.Passport;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.impl.PassportDAOImpl;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.mapper.user_account.PassportDTOMapper;
import com.epam.jwd.service.dto.user_account.PassportDTO;
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

public class PassportService implements Service<PassportDTO, Integer> {

    private final DAO<Passport, Integer> passportDAO;
    private final DTOMapper<PassportDTO, Passport, Integer> mapper;

    private static final Logger log = LogManager.getLogger(PassportService.class);

    public PassportService() {
        this.passportDAO = PassportDAOImpl.getInstance();
        this.mapper = new PassportDTOMapper();
    }

    @Override
    public PassportDTO save(PassportDTO passportDTO) throws ServiceException {

        Passport passport = mapper.convertToEntity(passportDTO);

        try {
            passportDTO = mapper.convertToDTO(passportDAO.save(passport));
        } catch (DAOException e) {
            log.error(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
        }

        return passportDTO;
    }

    @Override
    public List<PassportDTO> findAll() throws ServiceException {
        List<PassportDTO> passports = new ArrayList<>();

        try {
            for(Passport passport : passportDAO.findAll()) {
                passports.add(mapper.convertToDTO(passport));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return passports;
    }

    @Override
    public PassportDTO findById(Integer id) throws ServiceException {

        PassportDTO passportDTO;

        try {
            passportDTO = mapper.convertToDTO(passportDAO.findById(id));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return passportDTO;
    }

    @Override
    public PassportDTO update(PassportDTO passportDTO) throws ServiceException {
        Passport passport = mapper.convertToEntity(passportDTO);

        try {
            passportDTO = mapper.convertToDTO(passportDAO.update(passport));
        } catch (DAOException e) {
            log.error(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
        }

        return passportDTO;
    }

    @Override
    public void delete(PassportDTO passportDTO) throws ServiceException {

        Passport passport = mapper.convertToEntity(passportDTO);

        try {
            passportDAO.delete(passport);
        } catch (DAOException e) {
            log.error(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
        }
    }
}
