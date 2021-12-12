package com.epam.jwd.dao.connection_pool.api;

import com.epam.jwd.dao.exception.DAOException;

import java.sql.Connection;

/**
 * @author mikh
 * <p>
 * This interface provids connectionPool description with usefull methods which porvide
 * user with initializing pool, shutdown poll, take and return connections to the pool
 */
public interface ConnectionPool {

    /**
     * Method which create the connection pool with several connections
     *
     * @throws DAOException if connection can't be accepted or JDBC driver is unavailable
     */
    void init() throws DAOException;

    /**
     * Method which close all connections and stops connection pool and opportunity to use connections
     *
     * @throws DAOException if connection can't be accepted or JDBC driver is unavailable
     */
    void shutDown() throws DAOException;

    /**
     * Method which provides user with option to take connection from pool
     *
     * @return taken connection from pool of class {@link Connection}
     */
    Connection takeConnection();

    /**
     * Method which returns taken connection to the pool
     *
     * @param connection connection to return to pool
     */
    void returnConnection(Connection connection);
}
