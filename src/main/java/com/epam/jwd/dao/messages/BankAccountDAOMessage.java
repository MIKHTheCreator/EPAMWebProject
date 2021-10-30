package com.epam.jwd.dao.messages;

public interface BankAccountDAOMessage {

    String SQL_SAVE_BANK_ACCOUNT_QUERY = "INSERT INTO bank_account (balance, currency," +
            " is_blocked) VALUES (?, ?, ?)";
    String SQL_FIND_ALL_BANK_ACCOUNTS_QUERY = "SELECT (bank_account_id, balance, currency, is_blocked) FROM bank_account";
    String SQL_FIND_BANK_ACCOUNT_BY_ID_QUERY = "SELECT (bank_account_id, balance, currency, is_blocked) FROM bank_account WHERE bank_account_id=?";
    String SQL_UPDATE_BANK_ACCOUNT_QUERY = "UPDATE bank_account SET balance=? currency=?" +
            " is_blocked=? WHERE bank_account_id=?";
    String SQL_DELETE_BANK_ACCOUNT_QUERY = "DELETE FROM bank_account WHERE bank_account_id=?";
}
