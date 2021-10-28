package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.user_account.Client;
import com.epam.jwd.dao.entity.user_account.User;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.api.PasswordManager;
import com.epam.jwd.service.impl.PasswordManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_DELETE_CLIENT_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_FIND_ALL_CLIENTS_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_FIND_BY_ID_CLIENT_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_SAVE_CLIENT_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_UPDATE_CLIENT_QUERY;
import static com.epam.jwd.dao.messages.ExceptionMessage.DELETE_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.DELETE_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.DELIMITER;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_ALL_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_ALL_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_BY_ID_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_BY_ID_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.ROLLBACK_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.ROLLBACK_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_EXCEPTION_CODE;

public class ClientDAOImpl implements DAO<Client, Integer> {

    private static DAO<Client, Integer> instance;

    private final ConnectionPool connectionPool;
    private final PasswordManager passwordManager;
    private final DAO<User, Integer> userDAO;

    private static final Logger log = LogManager.getLogger(ClientDAOImpl.class);

    static {
        instance = new ClientDAOImpl();
    }

    private ClientDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
        this.passwordManager = new PasswordManagerImpl();
        this.userDAO = UserDAOImpl.getInstance();
    }

    public static DAO<Client, Integer> getInstance() {
        synchronized (ClientDAOImpl.class) {
            if (instance == null) {
                instance = new ClientDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public Client save(Client client) throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_SAVE_CLIENT_QUERY);
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.executeQuery();

            userDAO.save(client.getUser());

            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
                throw new DAOException(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
            }

            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
    }

    @Override
    public List<Client> findAll() throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;
        List<Client> clients = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_FIND_ALL_CLIENTS_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                clients.add(createClient(resultSet));
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
                throw new DAOException(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
            }

            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return clients;
    }

    @Override
    public Client findById(Integer id)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_FIND_BY_ID_CLIENT_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return createClient(resultSet);
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
                throw new DAOException(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
            }

            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
    }

    @Override
    public Client update(Client client)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CLIENT_QUERY);
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.setInt(4, client.getId());
            statement.executeUpdate();

            return client;
        } catch (SQLException exception) {
            log.error(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
            throw new DAOException(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Client client)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_CLIENT_QUERY);
            statement.setInt(1, client.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
            throw new DAOException(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

    }

    private Client createClient(ResultSet resultSet)
            throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt(1));
        client.setUsername(resultSet.getString(2));
        client.setEmail(resultSet.getString(3));
        client.setPassword(passwordManager.decode(resultSet.getString(4)));
        client.setUser(userDAO.findUserByClientId(resultSet.getInt(1)));

        return client;
    }
}
