package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ClientDAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.user_account.Client;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.password_manager.api.PasswordManager;
import com.epam.jwd.service.password_manager.PasswordManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.message.ClientDAOMessage.*;
import static com.epam.jwd.dao.message.ExceptionMessage.DELETE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.DELETE_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_ALL_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_ALL_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_BY_ID_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_BY_ID_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.SAVE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.SAVE_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.UPDATE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.UPDATE_EXCEPTION_CODE;

public class ClientDAOImpl implements ClientDAO<Client, Integer> {

    private static ClientDAO<Client, Integer> instance;

    private final ConnectionPool connectionPool;
    private final PasswordManager passwordManager;

    private static final Logger log = LogManager.getLogger(ClientDAOImpl.class);

    static {
        instance = new ClientDAOImpl();
    }

    private ClientDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
        this.passwordManager = new PasswordManagerImpl();
    }

    public static ClientDAO<Client, Integer> getInstance() {
        synchronized (ClientDAOImpl.class) {
            if (instance == null) {
                instance = new ClientDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public Client save(Client client) throws DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_SAVE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

            saveClient(statement, client);
        } catch (SQLException exception) {
            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        }

        return client;
    }

    @Override
    public List<Client> findAll() throws DAOException {
        PreparedStatement statement;
        List<Client> clients;

        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_ALL_CLIENTS_QUERY);

            clients = findClients(statement);

        } catch (SQLException exception) {
            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        }

        return clients;
    }

    @Override
    public Client findById(Integer id)
            throws DAOException {
        PreparedStatement statement;
        Client client;

        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_BY_ID_CLIENT_QUERY);
            statement.setInt(1, id);

            client = findClient(statement);
        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        }

        return client;
    }

    @Override
    public Client update(Client client)
            throws DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_UPDATE_CLIENT_QUERY);

            updateClient(statement, client);
        } catch (SQLException exception) {
            log.error(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
            throw new DAOException(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
        }

        return client;
    }

    @Override
    public void delete(Client client)
            throws DAOException {

        try (Connection connection = connectionPool.takeConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CLIENT_QUERY)) {
                statement.setInt(1, client.getId());
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            log.error(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
            throw new DAOException(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
        }
    }

    @Override
    public Client findByUsername(String username) throws DAOException {
        PreparedStatement statement;
        Client client;

        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_BY_USERNAME_CLIENT_QUERY);
            statement.setString(1, username);

            client = findClient(statement);
        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        }

        return client;

    }

    private Client createClient(ResultSet resultSet)
            throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt(1));
        client.setUsername(resultSet.getString(2));
        client.setEmail(resultSet.getString(3));
        client.setPassword(passwordManager.decode(resultSet.getString(4)));

        return client;
    }

    private List<Client> findClients(PreparedStatement statement) throws SQLException {

        try (statement; ResultSet resultSet = statement.executeQuery()) {
            List<Client> clients = new ArrayList<>();

            while (resultSet.next()) {
                clients.add(createClient(resultSet));
            }

            return clients;
        }
    }

    private Client findClient(PreparedStatement statement) throws SQLException {

        try (statement; ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return createClient(resultSet);
            }

            return null;
        }
    }

    private void updateClient(PreparedStatement statement, Client client) throws SQLException {
        try (statement) {
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.setInt(4, client.getId());
            statement.executeUpdate();
        }
    }

    private void saveClient(PreparedStatement statement, Client client) throws SQLException {

        ResultSet resultSet = null;

        try {
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }
        } finally {
            statement.close();
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
