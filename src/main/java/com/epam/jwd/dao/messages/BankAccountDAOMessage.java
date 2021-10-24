package com.epam.jwd.dao.messages;

public interface BankAccountDAOMessage {

    String SQL_INSERT_QUERY = "INSERT INTO bank_account (account_balance, account_currency," +
            " is_blocked) VALUES (?, ?, ?)";
    String SQL_FIND_ALL_QUERY = "SELECT * FROM bank_account";
    String SQL_FIND_BY_ID_QUERY = "SELECT * FROM bank_account WHERE bank_account_id=?";
    String SQL_UPDATE_QUERY = "UPDATE bank_account SET account_balance=? account_currency=?" +
            " is_blocked=? WHERE bank_account_id=?";
    String SQL_DELETE_QUERY = "DELETE FROM bank_account WHERE bank_account_id=?";
    String SQL_FIND_BANK_ACCOUNT_BY_CREDIT_CARD_ID = "SELECT * FROM bank_account WHERE bank_account_id=?";
}
