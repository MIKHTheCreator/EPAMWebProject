package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ClientDAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.AbstractEntity;
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
import java.util.Optional;

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

/**
 * ClientDAO implementation class of ClintDAO for Client with Integer id
 *
 * @see ClientDAO
 */
public class ClientDAOImpl implements ClientDAO<Client, Integer> {

    private static ClientDAO<Client, Integer> instance;

    private final ConnectionPool connectionPool;
    private final PasswordManager passwordManager;

    private static final Logger log = LogManager.getLogger(ClientDAOImpl.class);

    static {
        instance = new ClientDAOImpl();
    }

    /**
     * Private constructor for initializing fields
     */
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

    /**
     * @see ClientDAO#save(AbstractEntity)
     */
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

    /**
     * @see ClientDAO#findAll()
     */
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

    /**
     * @see ClientDAO#findById(Object)
     */
    @Override
    public Client findById(Integer id)
            throws DAOException {
        PreparedStatement statement;
        Client client;

        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_BY_ID_CLIENT_QUERY);
            statement.setInt(1, id);

            client = findClient(statement).orElse(new Client());
        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        }

        return client;
    }

    /**
     * @see ClientDAO#update(AbstractEntity)
     */
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

    /**
     * @see ClientDAO#delete(AbstractEntity)
     */
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

    /**
     * @see ClientDAO#findByUsername(String)
     */
    @Override
    public Client findByUsername(String username) throws DAOException {
        PreparedStatement statement;
        Client client;

        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_BY_USERNAME_CLIENT_QUERY);
            statement.setString(1, username);

            client = findClient(statement).orElse(new Client());
        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        }

        return client;

    }

    /**
     * Method for creating Client entity with extracted fields
     *
     * @param resultSet query with different columns
     * @return Created Client with generated id
     * @throws SQLException if it's unable to take data from query
     * @see ResultSet
     */
    private Client createClient(ResultSet resultSet)
            throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt(1));
        client.setUsername(resultSet.getString(2));
        client.setEmail(resultSet.getString(3));
        client.setPassword(passwordManager.decode(resultSet.getString(4)));

        return client;
    }

    /**
     * Method for find all Clients in DB by provided id
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @return List of Clients with provided id field
     * @throws SQLException if it's unable to take query from DB
     */
    private List<Client> findClients(PreparedStatement statement) throws SQLException {

        try (statement; ResultSet resultSet = statement.executeQuery()) {
            List<Client> clients = new ArrayList<>();

            while (resultSet.next()) {
                clients.add(createClient(resultSet));
            }

            return clients;
        }
    }

    /**
     * Method for creating Client
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @return created Client
     * @throws SQLException if it's unable to take data from DB
     */
    private Optional<Client> findClient(PreparedStatement statement) throws SQLException {

        try (statement; ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return Optional.of(createClient(resultSet));
            }

            return Optional.empty();
        }
    }

    /**
     * Method for updating Client
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @param client    Client entity to update
     * @throws SQLException if it's unable to update DB
     */
    private void updateClient(PreparedStatement statement, Client client) throws SQLException {
        try (statement) {
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.setInt(4, client.getId());
            statement.executeUpdate();
        }
    }

    /**
     * Method for saving Client in DB
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @param client    Client entity for saving
     * @throws SQLException if it's unable to update DB
     */
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
