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

/**
 * @author mikh
 * Class which is an implementation of the ConnectioPool interface {@link ConnectionPool}
 * Class proiveds realization of all methods and as a pool collection there introduced BlockingQueue
 * whcich contains ProxyConnections {@link ProxyConnection}
 * Class created as a singleton and provides thread-safe method realization and collections
 */
public final class ConnectionPoolImpl implements ConnectionPool {

    /**
     * Instance of ConnectionPool
     */
    private static ConnectionPool instance = new ConnectionPoolImpl();
    /**
     * BlockingQueue which contains ProxyConnections {@link ProxyConnection} that are available to take
     */
    private final BlockingQueue<ProxyConnection> availableConnections;
    /**
     * BlockingQueue which contains ProxyConnections {@link ProxyConnection} that are not available to take
     */
    private final BlockingQueue<ProxyConnection> givenAwayConnections;
    /**
     * Field which shows current state of connection pool
     */
    private boolean initialized = false;

    private static final Logger log = LogManager.getLogger(ConnectionPoolImpl.class);

    /**
     * Constructor which initialize available and given away connections
     *
     * @see ConnectionPoolImpl#availableConnections
     * @see ConnectionPoolImpl#givenAwayConnections
     */
    private ConnectionPoolImpl() {
        this.availableConnections = new ArrayBlockingQueue<>(INITIAL_SIZE);
        this.givenAwayConnections = new ArrayBlockingQueue<>(INITIAL_SIZE);
    }

    /**
     * Singleton realization of given instance back to user
     *
     * @return Connection pool instance in one copy
     */
    public static ConnectionPool getInstance() {
        synchronized (ConnectionPool.class) {
            if (instance == null) {
                instance = new ConnectionPoolImpl();
                return instance;
            }
        }

        return instance;
    }

    /**
     * @see ConnectionPool#init()
     */
    @Override
    public void init()
            throws DAOException {

        if (!initialized) {
            createConnections();
            initialized = true;
        }

    }

    /**
     * @see ConnectionPool#shutDown()
     */
    @Override
    public void shutDown() throws DAOException {
        if (initialized) {
            closeConnections();
            initialized = false;
        }

    }

    /**
     * Method takes connection from collection {@link ConnectionPoolImpl#availableConnections} and put
     * it in {@link ConnectionPoolImpl#givenAwayConnections}
     * and returns it to user
     *
     * @see ConnectionPool#takeConnection()
     */
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

    /**
     * Method which takes connection from {@link ConnectionPoolImpl#givenAwayConnections}
     * and puts it to {@link ConnectionPoolImpl#availableConnections}
     *
     * @param connection connection to return to pool
     * @see ConnectionPool#returnConnection(Connection)
     */
    @Override
    public void returnConnection(Connection connection) {
        try {
            availableConnections.put(givenAwayConnections.take());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(INTERRUPT_EXCEPTION + DELIMITER + INTERRUPT_EXCEPTION_CODE);
        }
    }

    /**
     * Method which creates connection pool of INITIAL_SIZE {@link com.epam.jwd.dao.message.SQLConfig#INITIAL_SIZE}
     * It creates ProxyConnections {@link ProxyConnection} and puts it to {@link ConnectionPoolImpl#availableConnections}
     *
     * @throws DAOException if SQL driver is unavailable
     */
    private void createConnections() throws DAOException {

        try {

            for (int i = 0; i < INITIAL_SIZE; i++) {
                Class.forName(SQL_DB_DRIVER);
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

    /**
     * Method which close connections
     *
     * @see ConnectionPoolImpl#closeConnections(Collection)
     */
    private void closeConnections() throws DAOException {
        closeConnections(availableConnections);
        closeConnections(givenAwayConnections);
    }

    /**
     * Method which takes collection with ProxyConnection and close connections
     *
     * @param connections collection with ProxyConnections
     * @see ConnectionPoolImpl#closeConnection(ProxyConnection)
     */
    private void closeConnections(Collection<ProxyConnection> connections) throws DAOException {
        for (ProxyConnection connection : connections) {
            closeConnection(connection);
        }
    }

    /**
     * Method which close one connection
     * It calls close method on ProxyConnection
     *
     * @param connection connection to close
     * @throws DAOException if can't close current connection {@link Connection#close()}
     * @see ProxyConnection#close()
     */
    private void closeConnection(ProxyConnection connection) throws DAOException {
        try {
            connection.realClose();
        } catch (SQLException e) {
            log.error(CONNECTION_EXCEPTION + DELIMITER + CONNECTION_EXCEPTION_CODE, e);
            throw new DAOException(CONNECTION_EXCEPTION + DELIMITER + CONNECTION_EXCEPTION_CODE, e);
        }
    }
}
