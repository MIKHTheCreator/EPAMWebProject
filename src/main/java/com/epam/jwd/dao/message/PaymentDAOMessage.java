package com.epam.jwd.dao.message;

/**
 * Interface which contains SQL Payment queries
 */
public interface PaymentDAOMessage {

    String SQL_SAVE_PAYMENT_QUERY = "INSERT INTO payment (sum, date, organization, goal, bank_account_id, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    String SQL_FIND_ALL_PAYMENTS_QUERY = "SELECT payment_id, sum, date, organization, goal, bank_account_id, user_id FROM payment";
    String SQL_FIND_ALL_PAYMENTS_BY_USER_ID_QUERY = "SELECT payment_id, sum, date, organization, goal, bank_account_id, user_id FROM payment WHERE user_id=?";
    String SQL_FIND_PAYMENT_BY_ID_QUERY = "SELECT payment_id, sum, date, organization, goal, bank_account_id, user_id FROM payment WHERE payment_id=?";
    String SQL_UPDATE_PAYMENT_QUERY = "UPDATE payment SET sum=?, date=?, organization=?, goal=?, bank_account_id=?, user_id=? WHERE payment_id=?";
    String SQL_DELETE_QUERY = "DELETE FROM payment WHERE payment_id=?";
    String SQL_FIND_ALL_PAYMENTS_BY_USER_ID_AND_PAGE_ID_QUERY = "SELECT payment_id, sum, date, organization, goal, bank_account_id, user_id FROM payment WHERE user_id=? LIMIT ?, ?";
}
