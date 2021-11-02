package com.epam.jwd.dao.connection_pool.api;

import com.epam.jwd.dao.exception.DAOException;

import java.sql.Connection;

public interface ConnectionPool {

    boolean init() throws DAOException;

    boolean shutDown() throws DAOException;

    Connection takeConnection();

    void returnConnection(Connection connection);
}
