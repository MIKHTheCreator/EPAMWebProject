package com.epam.jwd.service.impl.user_account;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.api.UserDAO;
import com.epam.jwd.dao.entity.user_account.User;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.impl.UserDAOImpl;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.mapper.user_account.UserDTOMapper;
import com.epam.jwd.service.dto.user_account.UserDTO;
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

public class UserService implements com.epam.jwd.service.api.UserService<UserDTO, Integer> {

    private final UserDAO<User, Integer> userDAO;
    private final DTOMapper<UserDTO, User, Integer> mapper;

    private static final Logger log = LogManager.getLogger(UserService.class);

    public UserService() {
        this.userDAO = UserDAOImpl.getInstance();
        this.mapper = new UserDTOMapper();
    }

    @Override
    public UserDTO save(UserDTO userDTO) throws ServiceException {
        User user = mapper.convertToEntity(userDTO);

        try {
            userDTO = mapper.convertToDTO(userDAO.save(user));
        } catch (DAOException e) {
            log.error(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> findAll() throws ServiceException {

        List<UserDTO> users = new ArrayList<>();

        try {
            for (User user : userDAO.findAll()) {
                users.add(mapper.convertToDTO(user));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return users;
    }

    @Override
    public UserDTO findById(Integer id) throws ServiceException {
        UserDTO userDTO;

        try {
            userDTO = mapper.convertToDTO(userDAO.findById(id));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO userDTO) throws ServiceException {
        User user = mapper.convertToEntity(userDTO);

        try {
            userDTO = mapper.convertToDTO(userDAO.update(user));
        } catch (DAOException e) {
            log.error(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
        }

        return userDTO;
    }

    @Override
    public void delete(UserDTO userDTO) throws ServiceException {
        User user = mapper.convertToEntity(userDTO);

        try {
            userDAO.delete(user);
        } catch (DAOException e) {
            log.error(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
        }
    }

    @Override
    public UserDTO findUserByClientId(Integer id) throws ServiceException {
        UserDTO userDTO;

        try {
            userDTO = mapper.convertToDTO(userDAO.findUserByClientId(id));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return userDTO;
    }
}
