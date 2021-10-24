package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ClientDAO;
import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.entity.Client;
import com.epam.jwd.dao.exception.DeleteFromDataBaseException;
import com.epam.jwd.dao.exception.FindInDataBaseException;
import com.epam.jwd.dao.exception.RollBackOperationException;
import com.epam.jwd.dao.exception.SaveOperationException;
import com.epam.jwd.dao.exception.UpdateDataBaseException;
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

import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_DELETE_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_FIND_ALL_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_FIND_BY_ID_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_FIND_CLIENT_BY_USER_ID_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_INSERT_QUERY;
import static com.epam.jwd.dao.messages.ClientDAOMessage.SQL_UPDATE_QUERY;
import static com.epam.jwd.dao.messages.ExceptionMessage.DELETE_ENTITY_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.DISABLE_AUTOCOMMIT_FLAG;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SQL_ROLLBACK_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_DATABASE_EXCEPTION_MESSAGE;

public class ClientDAOImpl implements ClientDAO {

    private static ClientDAO instance;

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

    public static ClientDAO getInstance() {
        synchronized (ClientDAOImpl.class) {
            if (instance == null) {
                instance = new ClientDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public Client save(Client client) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(SAVE_OPERATION_EXCEPTION_MESSAGE, exception);
            throw new SaveOperationException(SAVE_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return client;
    }

    @Override
    public List<Client> findAll() throws InterruptedException {
        List<Client> clients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Client client = createClient(resultSet);
                clients.add(client);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(FIND_OPERATION_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(FIND_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return clients;
    }

    @Override
    public Client findById(Integer id) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_BY_ID_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return createClient(resultSet);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
    }

    @Override
    public Client update(Client client) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_UPDATE_QUERY);
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.setInt(4, client.getId());
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(UPDATE_DATABASE_EXCEPTION_MESSAGE, exception);
            throw new UpdateDataBaseException(UPDATE_DATABASE_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return client;
    }

    @Override
    public void delete(Client client) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_DELETE_QUERY);
            statement.setInt(1, client.getId());
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(DELETE_ENTITY_EXCEPTION_MESSAGE, exception);
            throw new DeleteFromDataBaseException(DELETE_ENTITY_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Client findClientByUserId(Integer id) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_CLIENT_BY_USER_ID_QUERY);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return createClient(resultSet);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
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
}
