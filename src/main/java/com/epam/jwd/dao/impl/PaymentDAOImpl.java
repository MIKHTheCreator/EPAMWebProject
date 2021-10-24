package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.PaymentDAO;
import com.epam.jwd.dao.entity.Payment;
import com.epam.jwd.dao.exception.DeleteFromDataBaseException;
import com.epam.jwd.dao.exception.FindInDataBaseException;
import com.epam.jwd.dao.exception.SaveOperationException;
import com.epam.jwd.dao.exception.UpdateDataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private static PaymentDAO instance;

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    private static final String SQL_INSERT_QUERY = "INSERT INTO payment (sum_of_payment, date_of_payment," +
            " payment_organization, payment_goal, bank_account_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM payment";
    private static final String SQL_FIND_BY_ID_QUERY = "SELECT * FROM payment WHERE payment_id=?";
    private static final String SQL_UPDATE_QUERY = "UPDATE payment SET sum_of_payment=? date_of_payment=?" +
            "payment_organization=? payment_goal=? bank_account_id=? WHERE payment_id=?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM payment WHERE payment_id=?";
    private static final String SQL_FIND_ALL_PAYMENTS_BY_USER_ID = "SELECT * FROM payment WHERE bank_account_id=?";
    private static final String SQL_INSERT_EXCEPTION_MESSAGE = "Insert payment data to database was failed";
    private static final String SQL_FIND_ALL_EXCEPTION_MESSAGE = "Selecting payments data info from database was failed";
    private static final String SQL_FIND_BY_ID_EXCEPTION_MESSAGE = "There is no payment with such id in database";
    private static final String SQL_UPDATE_EXCEPTION_MESSAGE = "Updating payment information was failed";
    private static final String SQL_DELETE_EXCEPTION_MESSAGE = "Deleting payment with such id was failed";
    private static final Logger log = LogManager.getLogger(PaymentDAOImpl.class);

    static {
        instance = new PaymentDAOImpl();
    }

    private PaymentDAOImpl() {
    }

    public static PaymentDAO getInstance() {
        synchronized (PaymentDAOImpl.class) {
            if(instance == null) {
                instance = new PaymentDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public Payment save(Payment payment) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setBigDecimal(1, payment.getSumOfPayment());
            statement.setDate(2, Date.valueOf(payment.getDateOfPayment()));
            statement.setString(3, payment.getPaymentOrganization());
            statement.setString(4, payment.getPaymentGoal());
            statement.setInt(5, payment.getBankAccountId());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                payment.setId(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            log.error(SQL_INSERT_EXCEPTION_MESSAGE, exception);
            throw new SaveOperationException(SQL_INSERT_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return payment;
    }

    @Override
    public List<Payment> findAll() throws InterruptedException {
        List<Payment> payments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = createPayment(resultSet);

                payments.add(payment);
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_ALL_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_ALL_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return payments;
    }

    @Override
    public Payment findById(Integer id) throws InterruptedException {
        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_BY_ID_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {

                return createPayment(resultSet);
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_BY_ID_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_BY_ID_EXCEPTION_MESSAGE);
        }

        return null;
    }

    @Override
    public Payment update(Payment payment) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_QUERY);
            statement.setBigDecimal(1, payment.getSumOfPayment());
            statement.setDate(2, Date.valueOf(payment.getDateOfPayment()));
            statement.setString(3, payment.getPaymentOrganization());
            statement.setString(4, payment.getPaymentGoal());
            statement.setInt(5, payment.getBankAccountId());
            statement.setInt(6, payment.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(SQL_UPDATE_EXCEPTION_MESSAGE, exception);
            throw new UpdateDataBaseException(SQL_UPDATE_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return payment;
    }

    @Override
    public void delete(Payment payment) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_QUERY);
            statement.setInt(1, payment.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(SQL_DELETE_EXCEPTION_MESSAGE, exception);
            throw new DeleteFromDataBaseException(SQL_DELETE_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Payment> findAllByBankAccountId(Integer id) throws InterruptedException {
        List<Payment> payments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL_PAYMENTS_BY_USER_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getInt(1));
                payment.setSumOfPayment(resultSet.getBigDecimal(2));
                payment.setDateOfPayment(resultSet.getDate(3).toLocalDate());
                payment.setPaymentOrganization(resultSet.getString(4));
                payment.setPaymentGoal(resultSet.getString(5));
                payment.setBankAccountId(resultSet.getInt(6));

                payments.add(payment);
            }

        } catch (SQLException exception) {
            log.error(SQL_FIND_BY_ID_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_BY_ID_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return payments;
    }

    private Payment createPayment(ResultSet resultSet) throws SQLException {

        Payment payment = new Payment();
        payment.setId(resultSet.getInt(1));
        payment.setSumOfPayment(resultSet.getBigDecimal(2));
        payment.setDateOfPayment(resultSet.getDate(3).toLocalDate());
        payment.setPaymentOrganization(resultSet.getString(4));
        payment.setPaymentGoal(resultSet.getString(5));
        payment.setBankAccountId(resultSet.getInt(6));

        return payment;
    }
}
