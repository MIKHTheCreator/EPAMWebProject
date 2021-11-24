package com.epam.jwd.dao.message;

public interface UserDAOMessage {

    String SQL_SAVE_USER_QUERY = "INSERT INTO `user` (first_name, second_name, phone_number, age, gender, client_id, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String SQL_FIND_ALL_USERS_QUERY = "SELECT (user_id, first_name, second_name, phone_number, age, gender, client_id, passport_id, role_id) FROM user";
    String SQL_FIND_USER_BY_ID_QUERY = "SELECT (user_id, first_name, second_name, phone_number, age, gender, client_id, passport_id, role_id) FROM user WHERE user_id=?";
    String SQL_FIND_USER_BY_CLIENT_ID_QUERY = "SELECT user_id, first_name, second_name, phone_number, age, gender, client_id, passport_id, role_id FROM `user` WHERE client_id=?";
    String SQL_DELETE_USER_QUERY = "DELETE FROM user WHERE user_id=?";
    String SQL_UPDATE_USER_QUERY = "UPDATE user SET first_name=? second_name=? phone_number=? age =? gender=? client_id=? passport_id=? role_id=?" +
            " WHERE user_id = ?";
    String SQL_FIND_ROLE_BY_ID = "SELECT role_name FROM role WHERE role_id=?";
}
