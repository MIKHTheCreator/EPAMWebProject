package com.epam.jwd.service.impl.user_account;

import com.epam.jwd.dao.api.ClientDAO;
import com.epam.jwd.dao.entity.user_account.Client;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.impl.ClientDAOImpl;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.mapper.user_account.ClientDTOMapper;
import com.epam.jwd.service.dto.user_account.ClientDTO;
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

public class ClientService implements com.epam.jwd.service.api.ClientService<ClientDTO, Integer> {

    private final ClientDAO<Client, Integer> clientDAO;
    private final DTOMapper<ClientDTO, Client, Integer> mapper;

    private static final Logger log = LogManager.getLogger(ClientService.class);

    public ClientService() {
        this.clientDAO = ClientDAOImpl.getInstance();
        this.mapper = new ClientDTOMapper();
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) throws ServiceException {
        Client client = mapper.convertToEntity(clientDTO);

        try {
            clientDTO = mapper.convertToDTO(clientDAO.save(client));
        } catch (DAOException e) {
            log.error(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
        }

        return clientDTO;
    }

    @Override
    public List<ClientDTO> findAll() throws ServiceException {
        List<ClientDTO> clients = new ArrayList<>();

        try {
            for (Client client : clientDAO.findAll()) {
                clients.add(mapper.convertToDTO(client));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return clients;
    }

    @Override
    public ClientDTO findById(Integer id) throws ServiceException {
        ClientDTO clientDTO;

        try {
            clientDTO = mapper.convertToDTO(clientDAO.findById(id));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return clientDTO;
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) throws ServiceException {
        Client client = mapper.convertToEntity(clientDTO);

        try {
            clientDTO = mapper.convertToDTO(clientDAO.update(client));
        } catch (DAOException e) {
            log.error(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
        }

        return clientDTO;
    }

    @Override
    public void delete(ClientDTO clientDTO) throws ServiceException {
        Client client = mapper.convertToEntity(clientDTO);

        try {
            clientDAO.delete(client);
        } catch (DAOException e) {
            log.error(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
        }
    }

    @Override
    public ClientDTO findClientByUsername(String username) throws ServiceException {
        ClientDTO clientDTO;

        try {
            clientDTO = mapper.convertToDTO(clientDAO.findByUsername(username));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return clientDTO;
    }
}
