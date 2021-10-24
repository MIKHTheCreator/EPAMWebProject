package com.epam.jwd.dao.messages;

public interface ClientDAOMessage {

    String SQL_INSERT_QUERY = "INSERT INTO client (username, email, password)" +
            "VALUES (?, ?, ?)";
    String SQL_FIND_ALL_QUERY = "SELECT * FROM client";
    String SQL_FIND_BY_ID_QUERY = "SELECT * FROM client WHERE client_id=?";
    String SQL_UPDATE_QUERY = "UPDATE client SET username=? email=? passport=? WHERE client_id=?";
    String SQL_DELETE_QUERY = "DELETE FROM client WHERE client_id=?";
    String SQL_FIND_CLIENT_BY_USER_ID_QUERY = "SELECT * FROM client WHERE client_id = id";
}
