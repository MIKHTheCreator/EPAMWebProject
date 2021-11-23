package com.epam.jwd.dao.message;

public interface ClientDAOMessage {

    String SQL_SAVE_CLIENT_QUERY = "INSERT INTO `client` (username, email, password)" +
            "VALUES (?, ?, ?)";
    String SQL_FIND_ALL_CLIENTS_QUERY = "SELECT (client_id, username, email, password) FROM client";
    String SQL_FIND_BY_ID_CLIENT_QUERY = "SELECT (client_id, username, email, password) FROM client WHERE client_id=?";
    String SQL_FIND_BY_USERNAME_CLIENT_QUERY = "SELECT (client_id, username, email, password) FROM client WHERE username=?";
    String SQL_UPDATE_CLIENT_QUERY = "UPDATE client SET username=? email=? passport=? WHERE client_id=?";
    String SQL_DELETE_CLIENT_QUERY = "DELETE FROM client WHERE client_id=?";
}
