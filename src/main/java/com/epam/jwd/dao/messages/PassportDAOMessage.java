package com.epam.jwd.dao.messages;

public interface PassportDAOMessage {

    String SQL_INSERT_QUERY = "INSERT INTO passport_data (seria_and_number, personal_number, expiration_date)" +
            "VALUES (?, ?, ?)";
    String SQL_FIND_ALL_QUERY = "SELECT * FROM passport_data";
    String SQL_FIND_BY_ID_QUERY = "SELECT * FROM passport_data WHERE passport_id=?";
    String SQL_UPDATE_QUERY = "UPDATE passport_data SET seria_and_number=? personal_number=? expiration_date=? WHERE passport_id=?";
    String SQL_DELETE_QUERY = "DELETE FROM passport_data WHERE passport_id=?";
    String SQL_FIND_PASSPORT_BY_USER_ID_QUERY = "SELECT * FROM passport_data WHERE passport_id=?";
}
