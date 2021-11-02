package com.epam.jwd.dao.connection_pool.impl;

import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.epam.jwd.dao.message.ExceptionMessage.CONNECTION_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.CONNECTION_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.dao.message.ExceptionMessage.INTERRUPT_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.INTERRUPT_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.SQLConfig.INITIAL_SIZE;
import static com.epam.jwd.dao.message.SQLConfig.SQL_DB_DRIVER;
import static com.epam.jwd.dao.message.SQLConfig.SQL_DB_PASSWORD;
import static com.epam.jwd.dao.message.SQLConfig.SQL_DB_URL;
import static com.epam.jwd.dao.message.SQLConfig.SQL_DB_USERNAME;

public final class ConnectionPoolImpl implements ConnectionPool {

    private static ConnectionPool instance = new ConnectionPoolImpl();
    private final BlockingQueue<ProxyConnection> availableConnections;
    private final BlockingQueue<ProxyConnection> givenAwayConnections;
    private boolean initialized = false;

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
    public boolean init()
            throws DAOException {

        if(!initialized) {
            createConnections();
            initialized = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean shutDown() throws DAOException {
        if(initialized) {
            closeConnections();
            initialized = false;
            return true;
        }

        return false;
    }

    @Override
    public Connection takeConnection() {
        try {
            final ProxyConnection connection = availableConnections.take();
            givenAwayConnections.put(connection);
            return connection;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(INTERRUPT_EXCEPTION + DELIMITER + INTERRUPT_EXCEPTION_CODE);
        }

        return null;
    }

    @Override
    public void returnConnection(Connection connection) {
        try {
            availableConnections.put(givenAwayConnections.take());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(INTERRUPT_EXCEPTION + DELIMITER + INTERRUPT_EXCEPTION_CODE);
        }
    }

    private void createConnections() throws DAOException {

        try {
            Class.forName(SQL_DB_DRIVER);

            for (int i = 0; i < INITIAL_SIZE; i++) {
                final Connection connection = DriverManager.getConnection(SQL_DB_URL, SQL_DB_USERNAME, SQL_DB_PASSWORD);
                final ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                availableConnections.put(proxyConnection);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            log.error(CONNECTION_EXCEPTION + DELIMITER + CONNECTION_EXCEPTION_CODE, exception);
            throw new DAOException(CONNECTION_EXCEPTION + DELIMITER + CONNECTION_EXCEPTION_CODE, exception);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(INTERRUPT_EXCEPTION + DELIMITER + INTERRUPT_EXCEPTION_CODE);
        }
    }

    private void closeConnections() throws DAOException {
        closeConnections(availableConnections);
        closeConnections(givenAwayConnections);
    }

    private void closeConnections(Collection<ProxyConnection> connections) throws DAOException {
        for (ProxyConnection connection : connections) {
            closeConnection(connection);
        }
    }

    private void closeConnection(ProxyConnection connection) throws DAOException {
        try {
            connection.realClose();
        } catch (SQLException e) {
            log.error(CONNECTION_EXCEPTION + DELIMITER + CONNECTION_EXCEPTION_CODE, e);
            throw new DAOException(CONNECTION_EXCEPTION + DELIMITER + CONNECTION_EXCEPTION_CODE, e);
        }
    }
}
