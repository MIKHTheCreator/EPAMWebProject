package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.exception.ConnectionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPoolImpl implements ConnectionPool {

    private static ConnectionPool instance = new ConnectionPoolImpl();
    private final BlockingQueue<ProxyConnection> availableConnections;
    private final BlockingQueue<ProxyConnection> givenAwayConnections;
    private boolean initialized = false;

    private static final Integer INITIAL_SIZE = 4;
    private static final String SQL_DB_URL = "jdbc:mysql://localhost:3306/banksystem";
    private static final String SQL_DB_USERNAME = "mikh";
    private static final String SQL_DB_PASSWORD = "*652296D2A&C798A9C5053Ec5A72Ebf93BB026B8C";
    private static final String SQL_DB_DRIVER = "com.jdbc.mysql.Driver";
    private static final String CONNECTION_FAIL_EXCEPTION_MESSAGE = "Connection to the DataBase has been failed";
    private static final String CONNECTION_CREATION_LOG_MESSAGE = "Creation has been failed";
    private static final String CONNECTION_CLOSE_LOG_MESSAGE = "Closing connection has been failed";
    private static final Logger log = LogManager.getLogger(ConnectionPoolImpl.class);

    private ConnectionPoolImpl() {
        this.availableConnections = new ArrayBlockingQueue<>(INITIAL_SIZE);
        this.givenAwayConnections = new ArrayBlockingQueue<>(INITIAL_SIZE);
    }

    public static ConnectionPool getInstance() {
        synchronized (ConnectionPool.class) {
            if(instance == null) {
                instance = new ConnectionPoolImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public boolean init() throws InterruptedException {

        if(!initialized) {
            createConnections(INITIAL_SIZE);
            initialized = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean shutDown() {
        if(initialized) {
            closeConnections();
            initialized = false;
            return true;
        }

        return false;
    }

    @Override
    public Connection takeConnection() throws InterruptedException {
        final ProxyConnection connection = availableConnections.take();
        givenAwayConnections.put(connection);

        return connection;
    }

    @Override
    public void returnConnection(Connection connection) throws InterruptedException {
        availableConnections.put(givenAwayConnections.take());
    }

    private void createConnections(int connectionPoolSize)
            throws InterruptedException, ConnectionFailedException {

        try {
            Class.forName(SQL_DB_DRIVER);

            for (int i = 0; i < connectionPoolSize; i++) {
                final Connection connection = DriverManager.getConnection(SQL_DB_URL, SQL_DB_USERNAME, SQL_DB_PASSWORD);
                final ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                availableConnections.put(proxyConnection);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            log.error(CONNECTION_CREATION_LOG_MESSAGE);
            throw new ConnectionFailedException(CONNECTION_FAIL_EXCEPTION_MESSAGE);
        }
    }

    private void closeConnections() {
        closeConnections(availableConnections);
        closeConnections(givenAwayConnections);
    }

    private void closeConnections(Collection<ProxyConnection> connections) {
        for (ProxyConnection connection : connections) {
            closeConnection(connection);
        }
    }

    private void closeConnection(ProxyConnection connection) {
        try {
            connection.realClose();
        } catch (SQLException e) {
            log.error(CONNECTION_CLOSE_LOG_MESSAGE);
        }
    }
}
