package com.epam.jwd.dao.messages;

public interface PaymentDAOMessage {

    String SQL_INSERT_QUERY = "INSERT INTO payment (sum_of_payment, date_of_payment," +
            " payment_organization, payment_goal, bank_account_id) VALUES (?, ?, ?, ?, ?)";
    String SQL_FIND_ALL_QUERY = "SELECT * FROM payment";
    String SQL_FIND_BY_ID_QUERY = "SELECT * FROM payment WHERE payment_id=?";
    String SQL_UPDATE_QUERY = "UPDATE payment SET sum_of_payment=? date_of_payment=?" +
            "payment_organization=? payment_goal=? bank_account_id=? WHERE payment_id=?";
    String SQL_DELETE_QUERY = "DELETE FROM payment WHERE payment_id=?";
    String SQL_FIND_ALL_PAYMENTS_BY_USER_ID = "SELECT * FROM payment WHERE bank_account_id=?";
}
