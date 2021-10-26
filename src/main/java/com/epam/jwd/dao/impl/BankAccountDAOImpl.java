package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.BankAccountDAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.api.PaymentDAO;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DeleteFromDataBaseException;
import com.epam.jwd.dao.exception.FindInDataBaseException;
import com.epam.jwd.dao.exception.RollBackOperationException;
import com.epam.jwd.dao.exception.SaveOperationException;
import com.epam.jwd.dao.exception.UpdateDataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_DELETE_QUERY;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_FIND_ALL_QUERY;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_FIND_BANK_ACCOUNT_BY_CREDIT_CARD_ID;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_FIND_BY_ID_QUERY;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_INSERT_QUERY;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_UPDATE_QUERY;
import static com.epam.jwd.dao.messages.ExceptionMessage.DELETE_ENTITY_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.DISABLE_AUTOCOMMIT_FLAG;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SQL_ROLLBACK_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_DATABASE_EXCEPTION_MESSAGE;


public class BankAccountDAOImpl implements BankAccountDAO {

    private static BankAccountDAO instance;

    private final ConnectionPool connectionPool;
    private final PaymentDAO paymentDAO;

    private static final Logger log = LogManager.getLogger(BankAccountDAOImpl.class);

    static {
        instance = new BankAccountDAOImpl();
    }

    private BankAccountDAOImpl() {
        this.paymentDAO = PaymentDAOImpl.getInstance();
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    public static BankAccountDAO getInstance() {
        synchronized (BankAccountDAOImpl.class) {
            if (instance == null) {
                instance = new BankAccountDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public BankAccount save(BankAccount bankAccount) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setBigDecimal(1, bankAccount.getAccountBalance());
            statement.setString(2, bankAccount.getAccountCurrency());
            statement.setBoolean(3, bankAccount.isBlocked());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                bankAccount.setId(resultSet.getInt(1));
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(SAVE_OPERATION_EXCEPTION_MESSAGE, exception);
            throw new SaveOperationException(SAVE_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return bankAccount;
    }

    @Override
    public List<BankAccount> findAll() throws InterruptedException {
        List<BankAccount> bankAccounts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BankAccount bankAccount = createBankAccount(resultSet);

                bankAccounts.add(bankAccount);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(FIND_OPERATION_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(FIND_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return bankAccounts;
    }

    @Override
    public BankAccount findById(Integer id) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_BY_ID_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return createBankAccount(resultSet);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
    }

    @Override
    public BankAccount update(BankAccount bankAccount) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_UPDATE_QUERY);
            statement.setBigDecimal(1, bankAccount.getAccountBalance());
            statement.setString(2, bankAccount.getAccountCurrency());
            statement.setBoolean(3, bankAccount.isBlocked());
            statement.setInt(4, bankAccount.getId());
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(UPDATE_DATABASE_EXCEPTION_MESSAGE, exception);
            throw new UpdateDataBaseException(UPDATE_DATABASE_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return bankAccount;
    }

    @Override
    public void delete(BankAccount bankAccount) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_DELETE_QUERY);
            statement.setInt(1, bankAccount.getId());
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(DELETE_ENTITY_EXCEPTION_MESSAGE, exception);
            throw new DeleteFromDataBaseException(DELETE_ENTITY_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public BankAccount findBankAccountByCreditCardId(Integer id) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_BANK_ACCOUNT_BY_CREDIT_CARD_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return createBankAccount(resultSet);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
    }

    private BankAccount createBankAccount(ResultSet resultSet)
            throws SQLException, InterruptedException {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(resultSet.getInt(1));
        bankAccount.setAccountBalance(resultSet.getBigDecimal(2));
        bankAccount.setAccountCurrency(resultSet.getString(3));
        bankAccount.setBlocked(resultSet.getBoolean(4));
        bankAccount.setPayments(paymentDAO.findAllByBankAccountId(bankAccount.getId()));

        return bankAccount;
    }
}
