package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.PaymentDAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.entity.payment_system.Payment;
import com.epam.jwd.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.message.ExceptionMessage.DELETE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.DELETE_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_ALL_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_ALL_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_BY_ID_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_BY_ID_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.SAVE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.SAVE_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.UPDATE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.UPDATE_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.PaymentDAOMessage.SQL_DELETE_QUERY;
import static com.epam.jwd.dao.message.PaymentDAOMessage.SQL_FIND_ALL_PAYMENTS_BY_USER_ID_AND_PAGE_ID_QUERY;
import static com.epam.jwd.dao.message.PaymentDAOMessage.SQL_FIND_ALL_PAYMENTS_BY_USER_ID_QUERY;
import static com.epam.jwd.dao.message.PaymentDAOMessage.SQL_FIND_ALL_PAYMENTS_QUERY;
import static com.epam.jwd.dao.message.PaymentDAOMessage.SQL_FIND_PAYMENT_BY_ID_QUERY;
import static com.epam.jwd.dao.message.PaymentDAOMessage.SQL_SAVE_PAYMENT_QUERY;
import static com.epam.jwd.dao.message.PaymentDAOMessage.SQL_UPDATE_PAYMENT_QUERY;

/**
 * Payment DAO implementation of Payment DAO for Payment entity with Integer id
 *
 * @see PaymentDAO
 */
public class PaymentDAOImpl implements PaymentDAO<Payment, Integer> {

    private static PaymentDAO<Payment, Integer> instance;

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    private static final Logger log = LogManager.getLogger(PaymentDAOImpl.class);

    static {
        instance = new PaymentDAOImpl();
    }

    private PaymentDAOImpl() {
    }

    public static PaymentDAO<Payment, Integer> getInstance() {
        synchronized (PaymentDAOImpl.class) {
            if (instance == null) {
                instance = new PaymentDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    /**
     * @see PaymentDAO#save(AbstractEntity)
     */
    @Override
    public Payment save(Payment payment)
            throws DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_SAVE_PAYMENT_QUERY, Statement.RETURN_GENERATED_KEYS);
            savePayment(statement, payment);

        } catch (SQLException exception) {
            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        }

        return payment;
    }

    /**
     * @see PaymentDAO#findAll()
     */
    @Override
    public List<Payment> findAll()
            throws DAOException {
        List<Payment> payments;

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_ALL_PAYMENTS_QUERY);

            payments = findAllPayments(statement);

        } catch (SQLException exception) {
            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        }

        return payments;
    }

    /**
     * @see PaymentDAO#findById(Object)
     */
    @Override
    public Payment findById(Integer id) throws DAOException {
        PreparedStatement statement;
        Payment payment;

        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_PAYMENT_BY_ID_QUERY);
            statement.setInt(1, id);
            payment = findPayment(statement);

        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        }

        return payment;
    }

    /**
     * @see PaymentDAO#update(AbstractEntity)
     */
    @Override
    public Payment update(Payment payment) throws DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_UPDATE_PAYMENT_QUERY);

            updatePayment(statement, payment);

        } catch (SQLException exception) {
            log.error(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
            throw new DAOException(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
        }

        return payment;
    }

    /**
     * @see PaymentDAO#delete(AbstractEntity)
     */
    @Override
    public void delete(Payment payment) throws DAOException {

        try (Connection connection = connectionPool.takeConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY)) {
                statement.setInt(1, payment.getId());
                statement.executeUpdate();
            }

        } catch (SQLException exception) {
            log.error(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
            throw new DAOException(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
        }
    }

    /**
     * @see PaymentDAO#findAllPaymentsByUserId(Object)
     */
    @Override
    public List<Payment> findAllPaymentsByUserId(Integer id) throws DAOException {
        List<Payment> payments;

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_ALL_PAYMENTS_BY_USER_ID_QUERY);
            statement.setInt(1, id);

            payments = findAllPayments(statement);

        } catch (SQLException exception) {
            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        }

        return payments;
    }

    /**
     * @see PaymentDAO#findPaymentsByUserIdAndPageLimit(Object, int, int)
     */
    @Override
    public List<Payment> findPaymentsByUserIdAndPageLimit(Integer id, int page, int numOfPayments) throws DAOException {
        List<Payment> payments;

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_ALL_PAYMENTS_BY_USER_ID_AND_PAGE_ID_QUERY);
            statement.setInt(1, id);
            statement.setInt(2, page);
            statement.setInt(3, numOfPayments);

            payments = findAllPayments(statement);

        } catch (SQLException exception) {
            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        }

        return payments;
    }

    /**
     * Method for creating Payment entity with extracted fields
     *
     * @param resultSet query with different columns
     * @return Created Payment with generated id
     * @throws SQLException if it's unable to take data from query
     * @see ResultSet
     */
    private Payment createPayment(ResultSet resultSet) throws SQLException {

        return new Payment.Builder()
                .withId(resultSet.getInt(1))
                .withSumOfPayment(resultSet.getBigDecimal(2))
                .withDateOfPayment(resultSet.getDate(3).toLocalDate())
                .withPaymentOrganization(resultSet.getString(4))
                .withPaymentGoal(resultSet.getString(5))
                .withBankAccountId(resultSet.getInt(6))
                .withUserId(resultSet.getInt(7))
                .build();
    }

    /**
     * Method for saving Payment in DB
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @param payment   Payment Data entity for saving
     * @throws SQLException if it's unable to update DB
     */
    private void savePayment(PreparedStatement statement, Payment payment) throws SQLException {

        ResultSet resultSet = null;

        try {
            statement.setBigDecimal(1, payment.getSumOfPayment());
            statement.setDate(2, Date.valueOf(payment.getDateOfPayment()));
            statement.setString(3, payment.getPaymentOrganization());
            statement.setString(4, payment.getPaymentGoal());
            statement.setInt(5, payment.getBankAccountId());
            statement.setInt(6, payment.getUserid());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                payment.setId(resultSet.getInt(1));
            }

        } finally {
            statement.close();
            if (resultSet != null) {
                resultSet.close();
            }
        }

    }

    /**
     * Method for find all Payments in DB by provided id
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @return List of Payments with provided id field
     * @throws SQLException if it's unable to take query from DB
     */
    private List<Payment> findAllPayments(PreparedStatement statement) throws SQLException {
        try (statement; ResultSet resultSet = statement.executeQuery()) {
            List<Payment> payments = new ArrayList<>();

            while (resultSet.next()) {
                Payment payment = createPayment(resultSet);

                payments.add(payment);
            }
            return payments;
        }
    }

    /**
     * Method for creating Payment
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @return created Payment
     * @throws SQLException if it's unable to take data from DB
     */
    private Payment findPayment(PreparedStatement statement) throws SQLException {
        try (statement; ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return createPayment(resultSet);
            }
        }

        return null;
    }

    /**
     * Method for updating Payment
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @param payment   Payment entity to update
     * @throws SQLException if it's unable to update DB
     */
    private void updatePayment(PreparedStatement statement, Payment payment) throws SQLException {
        try (statement) {
            statement.setBigDecimal(1, payment.getSumOfPayment());
            statement.setDate(2, Date.valueOf(payment.getDateOfPayment()));
            statement.setString(3, payment.getPaymentOrganization());
            statement.setString(4, payment.getPaymentGoal());
            statement.setInt(5, payment.getBankAccountId());
            statement.setInt(6, payment.getUserid());
            statement.setInt(7, payment.getId());
            statement.executeUpdate();
        }
    }
}
