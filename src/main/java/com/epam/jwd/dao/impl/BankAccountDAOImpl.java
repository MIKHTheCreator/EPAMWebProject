package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.BankAccountDAO;
import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.PaymentDAO;
import com.epam.jwd.dao.entity.BankAccount;
import com.epam.jwd.dao.exception.DeleteFromDataBaseException;
import com.epam.jwd.dao.exception.FindInDataBaseException;
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


public class BankAccountDAOImpl implements BankAccountDAO {

    private static BankAccountDAO instance;

    private final ConnectionPool connectionPool;
    private final PaymentDAO paymentDAO;

    private static final String SQL_INSERT_QUERY = "INSERT INTO bank_account (account_balance, account_currency," +
            " is_blocked) VALUES (?, ?, ?)";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM bank_account";
    private static final String SQL_FIND_BY_ID_QUERY = "SELECT * FROM bank_account WHERE bank_account_id=?";
    private static final String SQL_UPDATE_QUERY = "UPDATE bank_account SET account_balance=? account_currency=?" +
            " is_blocked=? WHERE bank_account_id=?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM bank_account WHERE bank_account_id=?";
    private static final String SQL_FIND_BANK_ACCOUNT_BY_CREDIT_CARD_ID = "SELECT * FROM bank_account WHERE bank_account_id=?";
    private static final String SQL_INSERT_EXCEPTION_MESSAGE = "Insert bank account data to database was failed";
    private static final String SQL_FIND_ALL_EXCEPTION_MESSAGE = "Selecting bank account data info from database was failed";
    private static final String SQL_FIND_BY_ID_EXCEPTION_MESSAGE = "There is no bank account with such id in database";
    private static final String SQL_UPDATE_EXCEPTION_MESSAGE = "Updating bank account information was failed";
    private static final String SQL_DELETE_EXCEPTION_MESSAGE = "Deleting bank account with such id was failed";
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
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setBigDecimal(1, bankAccount.getAccountBalance());
            statement.setString(2, bankAccount.getAccountCurrency());
            statement.setBoolean(3, bankAccount.isBlocked());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                bankAccount.setId(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            log.error(SQL_INSERT_EXCEPTION_MESSAGE, exception);
            throw new SaveOperationException(SQL_INSERT_EXCEPTION_MESSAGE);
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
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BankAccount bankAccount = createBankAccount(resultSet);

                bankAccounts.add(bankAccount);
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_ALL_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_ALL_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return bankAccounts;
    }

    @Override
    public BankAccount findById(Integer id) throws InterruptedException {
        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_BY_ID_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return createBankAccount(resultSet);
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_BY_ID_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_BY_ID_EXCEPTION_MESSAGE);
        }

        return null;
    }

    @Override
    public BankAccount update(BankAccount bankAccount) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_QUERY);
            statement.setBigDecimal(1, bankAccount.getAccountBalance());
            statement.setString(2, bankAccount.getAccountCurrency());
            statement.setBoolean(3, bankAccount.isBlocked());
            statement.setInt(4, bankAccount.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(SQL_UPDATE_EXCEPTION_MESSAGE, exception);
            throw new UpdateDataBaseException(SQL_UPDATE_EXCEPTION_MESSAGE);
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
            statement = connection.prepareStatement(SQL_DELETE_QUERY);
            statement.setInt(1, bankAccount.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(SQL_DELETE_EXCEPTION_MESSAGE, exception);
            throw new DeleteFromDataBaseException(SQL_DELETE_EXCEPTION_MESSAGE);
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
            statement = connection.prepareStatement(SQL_FIND_BANK_ACCOUNT_BY_CREDIT_CARD_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return createBankAccount(resultSet);
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_BY_ID_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_BY_ID_EXCEPTION_MESSAGE);
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
