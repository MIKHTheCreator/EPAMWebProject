package com.epam.jwd.dao.api;

import java.sql.Connection;

public interface ConnectionPool {

    boolean init() throws InterruptedException;

    boolean shutDown();

    Connection takeConnection() throws InterruptedException;

    void returnConnection(Connection connection) throws InterruptedException;
}
