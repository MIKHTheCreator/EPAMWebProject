package com.epam.jwd.dao.message;

public interface CreditCardDAOMessage {

    String SQL_SAVE_CREDIT_CARD_QUERY = "INSERT INTO credit_card (number, expiration_date, full_name, cvv, pin, user_id, bank_account_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String SQL_FIND_ALL_CREDIT_CARDS_QUERY = "SELECT credit_card_id, number, expiration_date, full_name, cvv, pin, user_id, bank_account_id FROM credit_card";
    String SQL_FIND_ALL_CREDIT_CARDS_BY_USER_ID_QUERY = "SELECT credit_card_id, number, expiration_date, full_name, cvv, pin, user_id, bank_account_id FROM credit_card WHERE user_id=?";
    String SQL_FIND_CREDIT_CARD_BY_ID_QUERY = "SELECT credit_card_id, number, expiration_date, full_name, cvv, pin, user_id, bank_account_id FROM credit_card WHERE credit_card_id=?";
    String SQL_UPDATE_CREDIT_CARD_QUERY = "UPDATE credit_card SET number=?, expiration=?, full_name=?, cvv=?, pin=?, user_id=?, bank_account_id =? WHERE credit_card_id=?";
    String SQL_DELETE_CREDIT_CARD_QUERY = "DELETE FROM credit_card WHERE credit_card_id=?";
    String SQL_FIND_CREDIT_CARD_BY_BANK_ACCOUNT_ID_QUERY = "SELECT credit_card_id, number, expiration_date, full_name, cvv, pin, user_id, bank_account_id FROM credit_card WHERE bank_account_id=?";
}
