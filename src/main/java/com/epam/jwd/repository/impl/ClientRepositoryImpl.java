package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.Repository;
import com.epam.jwd.repository.api.ConnectionPool;
import com.epam.jwd.repository.entity.Client;
import com.epam.jwd.repository.exception.DeleteFromDataBaseException;
import com.epam.jwd.repository.exception.FindInDataBaseException;
import com.epam.jwd.repository.exception.SaveOperationException;
import com.epam.jwd.repository.exception.UpdateDataBaseException;
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

public class ClientRepositoryImpl implements Repository<Client, Integer> {

    private static Repository<Client, Integer> instance = new ClientRepositoryImpl();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private final PasswordManager passwordManager = new PasswordManagerImpl();

    private static final String SQL_INSERT_QUERY = "INSERT INTO client (username, email, password)" +
            "VALUES (?, ?, ?)";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM client";
    private static final String SQL_FIND_BY_ID_QUERY = "SELECT * FROM client WHERE client_id=?";
    private static final String SQL_UPDATE_QUERY = "UPDATE client SET username=? email=? passport=? WHERE client_id=?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM client WHERE client_id=?";
    private static final String SQL_INSERT_EXCEPTION_MESSAGE = "Insert client data to database was failed";
    private static final String SQL_FIND_ALL_EXCEPTION_MESSAGE = "Selecting client data info from database was failed";
    private static final String SQL_FIND_BY_ID_EXCEPTION_MESSAGE = "There is no client with such id in database";
    private static final String SQL_UPDATE_EXCEPTION_MESSAGE = "Updating client information was failed";
    private static final String SQL_DELETE_EXCEPTION_MESSAGE = "Deleting client with such id was failed";
    private static final Logger log = LogManager.getLogger(ClientRepositoryImpl.class);

    private ClientRepositoryImpl() {
    }

    public static Repository<Client, Integer> getInstance() {
        synchronized (ClientRepositoryImpl.class) {
            if(instance == null) {
                instance = new ClientRepositoryImpl();
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
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            log.error(SQL_INSERT_EXCEPTION_MESSAGE);
            throw new SaveOperationException(SQL_INSERT_EXCEPTION_MESSAGE);
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
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt(1));
                client.setUsername(resultSet.getString(2));
                client.setEmail(resultSet.getString(3));
                client.setPassword(passwordManager.decode(resultSet.getString(4)));
                clients.add(client);
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_ALL_EXCEPTION_MESSAGE);
            throw new FindInDataBaseException(SQL_FIND_ALL_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return clients;
    }

    @Override
    public Client findById(Integer id) throws InterruptedException {
        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_BY_ID_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Client client = new Client();
                client.setId(id);
                client.setUsername(resultSet.getString(2));
                client.setEmail(resultSet.getString(3));
                client.setPassword(passwordManager.decode(resultSet.getString(4)));

                return client;
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_BY_ID_EXCEPTION_MESSAGE);
            throw new FindInDataBaseException(SQL_FIND_BY_ID_EXCEPTION_MESSAGE);
        }

        return null;
    }

    @Override
    public Client update(Client client) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_QUERY);
            statement.setString(1, client.getUsername());
            statement.setString(2, client.getEmail());
            statement.setString(3, passwordManager.encode(client.getPassword()));
            statement.setInt(4, client.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(SQL_UPDATE_EXCEPTION_MESSAGE);
            throw new UpdateDataBaseException(SQL_UPDATE_EXCEPTION_MESSAGE);
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
            statement = connection.prepareStatement(SQL_DELETE_QUERY);
            statement.setInt(1, client.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(SQL_DELETE_EXCEPTION_MESSAGE);
            throw new DeleteFromDataBaseException(SQL_DELETE_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
