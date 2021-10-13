package com.epam.jwd.dao.api;

import java.sql.Connection;

public interface ConnectionPool {

    boolean init();

    boolean shutDown();

    Connection takeConnection();

    void returnConnection();
}
