package com.epam.jwd.dao.messages;

public interface CreditCardDAOMessage {

    String SQL_INSERT_QUERY = "INSERT INTO credit_card (credit_card_number, credit_card_expiration_date," +
            " name_and_surname, cvv, password, user_id, bank_account_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String SQL_FIND_ALL_QUERY = "SELECT * FROM credit_card";
    String SQL_FIND_BY_ID_QUERY = "SELECT * FROM credit_card WHERE credit_card_id=?";
    String SQL_UPDATE_QUERY = "UPDATE credit_card SET credit_card_number=? credit_card_expiration=?" +
            " name_and_surname=? cvv=? password=? user_id=? WHERE credit_card_id=?";
    String SQL_DELETE_QUERY = "DELETE FROM credit_card WHERE credit_card_id=?";
    String SQL_FIND_ALL_CREDIT_CARDS_BY_USER_ID = "SELECT * FROM credit_card WHERE user_id=?";
}
