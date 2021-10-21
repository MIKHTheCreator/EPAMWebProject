package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.DAO;
import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.entity.Client;
import com.epam.jwd.dao.entity.CreditCard;
import com.epam.jwd.dao.exception.DeleteFromDataBaseException;
import com.epam.jwd.dao.exception.FindInDataBaseException;
import com.epam.jwd.dao.exception.SaveOperationException;
import com.epam.jwd.dao.exception.UpdateDataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditCardDAOImpl implements DAO<CreditCard, Integer> {

    private static DAO<CreditCard, Integer> instance = new CreditCardDAOImpl();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    private static final String SQL_INSERT_QUERY = "INSERT INTO credit_card (credit_card_number, credit_card_expiration_date," +
            " name_and_surname, cvv, password, user_id, bank_account_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM client";
    private static final String SQL_FIND_BY_ID_QUERY = "SELECT * FROM client WHERE client_id=?";
    private static final String SQL_UPDATE_QUERY = "UPDATE client SET username=? email=? passport=? WHERE client_id=?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM client WHERE client_id=?";
    private static final String SQL_INSERT_EXCEPTION_MESSAGE = "Insert client data to database was failed";
    private static final String SQL_FIND_ALL_EXCEPTION_MESSAGE = "Selecting client data info from database was failed";
    private static final String SQL_FIND_BY_ID_EXCEPTION_MESSAGE = "There is no client with such id in database";
    private static final String SQL_UPDATE_EXCEPTION_MESSAGE = "Updating client information was failed";
    private static final String SQL_DELETE_EXCEPTION_MESSAGE = "Deleting client with such id was failed";
    private static final Logger log = LogManager.getLogger(CreditCardDAOImpl.class);

    private CreditCardDAOImpl() {
    }

    public static DAO<CreditCard, Integer> getInstance() {
        synchronized (ClientDAOImpl.class) {
            if(instance == null) {
                instance = new CreditCardDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public CreditCard save(CreditCard creditCard) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setInt(1, creditCard.getCreditCardNumber());
            statement.setDate(2, Date.valueOf(creditCard.getCreditCardExpiration()));
            statement.setString(3, creditCard.getNameAndSurname());
            statement.setInt(4, creditCard.getCVV());
            statement.setInt(5, creditCard.getPassword());
            statement.setInt(6, creditCard.get);
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
