package com.epam.jwd.dao.message;

/**
 * Interface which contains SQL config parameters
 */
public interface SQLConfig {

    Integer INITIAL_SIZE = 4;
    String SQL_DB_URL = "jdbc:mysql://localhost:3306/banksystem";
    String SQL_DB_USERNAME = "mikh";
    String SQL_DB_PASSWORD = "*652296D2A7C798A9C5053Ec5A72Ebf93BB026B8C";
    String SQL_DB_DRIVER = "com.mysql.cj.jdbc.Driver";
}
