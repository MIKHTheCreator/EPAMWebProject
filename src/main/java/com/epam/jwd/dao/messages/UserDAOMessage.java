package com.epam.jwd.dao.messages;

public interface UserDAOMessage {

    String SQL_SAVE_USER_QUERY = "INSERT INTO user (first_name, second_name, phone_number" +
            "age, gender, client_id, passport_data_passport_id, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String SQL_FIND_ALL_QUERY = "SELECT (first_name, second_name, phone_number, age, gender) FROM user";
    String SQL_FIND_USER_BY_ID_QUERY = "SELECT * FROM user WHERE user_id=?";
    String SQL_DELETE_USER_QUERY = "DELETE FROM user WHERE user_id=?";
    String SQL_USER_UPDATE_QUERY = "UPDATE user SET first_name=? second_name=? phone_number=? age =?" +
            " WHERE user_id = ?";
    String SQL_FIND_ROLE_BY_ID = "SELECT role_name FROM role WHERE role_id=?";
}
