package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.payment_system.BankAccount;
import com.epam.jwd.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_DELETE_BANK_ACCOUNT_QUERY;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_FIND_ALL_BANK_ACCOUNTS_QUERY;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_FIND_BANK_ACCOUNT_BY_ID_QUERY;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_SAVE_BANK_ACCOUNT_QUERY;
import static com.epam.jwd.dao.messages.BankAccountDAOMessage.SQL_UPDATE_BANK_ACCOUNT_QUERY;
import static com.epam.jwd.dao.messages.ExceptionMessage.DELETE_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.DELETE_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.DELIMITER;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_ALL_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_ALL_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_BY_ID_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_BY_ID_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_EXCEPTION_CODE;


public class BankAccountDAOImpl implements DAO<BankAccount, Integer> {

    private static DAO<BankAccount, Integer> instance;

    private final ConnectionPool connectionPool;

    private static final Logger log = LogManager.getLogger(BankAccountDAOImpl.class);

    static {
        instance = new BankAccountDAOImpl();
    }

    private BankAccountDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    public static DAO<BankAccount, Integer> getInstance() {
        synchronized (BankAccountDAOImpl.class) {
            if (instance == null) {
                instance = new BankAccountDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public BankAccount save(BankAccount bankAccount) throws InterruptedException, DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_SAVE_BANK_ACCOUNT_QUERY);

            saveBankAccount(statement, bankAccount);
        } catch (SQLException exception) {
            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        }

        return bankAccount;
    }

    @Override
    public List<BankAccount> findAll() throws InterruptedException, DAOException {
        List<BankAccount> bankAccounts;

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_ALL_BANK_ACCOUNTS_QUERY);

            bankAccounts = findBankAccounts(statement);

        } catch (SQLException exception) {
            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        }

        return bankAccounts;
    }

    @Override
    public BankAccount findById(Integer id) throws InterruptedException, DAOException {
        PreparedStatement statement;
        BankAccount bankAccount;

        try(Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_BANK_ACCOUNT_BY_ID_QUERY);
            statement.setInt(1, id);

            bankAccount = findBankAccount(statement);
        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        }

        return bankAccount;
    }

    @Override
    public BankAccount update(BankAccount bankAccount) throws InterruptedException, DAOException {

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BANK_ACCOUNT_QUERY)) {
                updateBankAccount(statement, bankAccount);
            }

        } catch (SQLException exception) {
            log.error(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
            throw new DAOException(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
        }

        return bankAccount;
    }

    @Override
    public void delete(BankAccount bankAccount) throws InterruptedException, DAOException {

        try (Connection connection = connectionPool.takeConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BANK_ACCOUNT_QUERY)) {
                statement.setInt(1, bankAccount.getId());
                statement.executeUpdate();
            }

        } catch (SQLException exception) {
            log.error(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
            throw new DAOException(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
        }
    }

    private BankAccount createBankAccount(ResultSet resultSet)
            throws SQLException {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(resultSet.getInt(1));
        bankAccount.setBalance(resultSet.getBigDecimal(2));
        bankAccount.setCurrency(resultSet.getString(3));
        bankAccount.setBlocked(resultSet.getBoolean(4));

        return bankAccount;
    }

    private void saveBankAccount(PreparedStatement statement, BankAccount bankAccount) throws SQLException {
        ResultSet resultSet = null;

        try {
            statement.setBigDecimal(1, bankAccount.getBalance());
            statement.setString(2, bankAccount.getCurrency());
            statement.setBoolean(3, bankAccount.isBlocked());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                bankAccount.setId(resultSet.getInt(1));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
                statement.close();
            }

        }
    }

    private List<BankAccount> findBankAccounts(PreparedStatement statement) throws SQLException {

        try (statement; ResultSet resultSet = statement.executeQuery()) {

            List<BankAccount> bankAccounts = new ArrayList<>();


            while (resultSet.next()) {
                BankAccount bankAccount = createBankAccount(resultSet);

                bankAccounts.add(bankAccount);
            }

            return bankAccounts;
        }
    }

    private BankAccount findBankAccount(PreparedStatement statement) throws SQLException {

        try (statement; ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {

                return createBankAccount(resultSet);
            }
        }

        return null;
    }

    private void updateBankAccount(PreparedStatement statement, BankAccount bankAccount) throws SQLException {
        statement.setBigDecimal(1, bankAccount.getBalance());
        statement.setString(2, bankAccount.getCurrency());
        statement.setBoolean(3, bankAccount.isBlocked());
        statement.setInt(4, bankAccount.getId());
        statement.executeUpdate();
    }
}
