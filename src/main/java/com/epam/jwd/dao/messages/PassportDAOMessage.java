package com.epam.jwd.dao.messages;

public interface PassportDAOMessage {

    String SQL_SAVE_PASSPORT_DATA_QUERY = "INSERT INTO passport_data (seria_and_number, personal_number, expiration_date)" +
            "VALUES (?, ?, ?)";
    String SQL_FIND_ALL_PASSPORTS_QUERY = "SELECT (passport_id, seria_and_number, personal_number, expiration_date) FROM passport_data";
    String SQL_FIND_PASSPORT_BY_ID_QUERY = "SELECT (passport_id, seria_and_number, personal_number, expiration_date) FROM passport_data WHERE passport_id=?";
    String SQL_UPDATE_PASSPORT_QUERY = "UPDATE passport_data SET seria_and_number=? personal_number=? expiration_date=? WHERE passport_id=?";
    String SQL_DELETE_PASSPORT_QUERY = "DELETE FROM passport_data WHERE passport_id=?";
}
