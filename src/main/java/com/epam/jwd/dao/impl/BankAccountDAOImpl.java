package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.entity.payment_system.BankAccount;
import com.epam.jwd.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.message.BankAccountDAOMessage.SQL_DELETE_BANK_ACCOUNT_QUERY;
import static com.epam.jwd.dao.message.BankAccountDAOMessage.SQL_FIND_ALL_BANK_ACCOUNTS_QUERY;
import static com.epam.jwd.dao.message.BankAccountDAOMessage.SQL_FIND_BANK_ACCOUNT_BY_ID_QUERY;
import static com.epam.jwd.dao.message.BankAccountDAOMessage.SQL_SAVE_BANK_ACCOUNT_QUERY;
import static com.epam.jwd.dao.message.BankAccountDAOMessage.SQL_UPDATE_BANK_ACCOUNT_QUERY;
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

/**
 * Bank Account DAO implementation class for BankAccount entity with Integer id
 *
 * @see DAO
 */
public class BankAccountDAOImpl implements DAO<BankAccount, Integer> {

    /**
     * Instance of DAO(BankAccountDAOImpl)
     */
    private static DAO<BankAccount, Integer> instance;

    /**
     * Connection pool instance
     */
    private final ConnectionPool connectionPool;

    private static final Logger log = LogManager.getLogger(BankAccountDAOImpl.class);

    static {
        instance = new BankAccountDAOImpl();
    }

    /**
     * Private constructor which initialize Connection Pool
     */
    private BankAccountDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    /**
     * Static factory method which provides DAO instance
     *
     * @return DAO instance
     */
    public static DAO<BankAccount, Integer> getInstance() {
        synchronized (BankAccountDAOImpl.class) {
            if (instance == null) {
                instance = new BankAccountDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    /**
     * @see DAO#save(AbstractEntity)
     */
    @Override
    public BankAccount save(BankAccount bankAccount) throws DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_SAVE_BANK_ACCOUNT_QUERY, Statement.RETURN_GENERATED_KEYS);

            saveBankAccount(statement, bankAccount);
        } catch (SQLException exception) {
            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        }

        return bankAccount;
    }

    /**
     * @see DAO#findAll()
     */
    @Override
    public List<BankAccount> findAll() throws DAOException {
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

    /**
     * @see DAO#findById(Object)
     */
    @Override
    public BankAccount findById(Integer id) throws DAOException {
        PreparedStatement statement;
        BankAccount bankAccount;

        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_FIND_BANK_ACCOUNT_BY_ID_QUERY);
            statement.setInt(1, id);

            bankAccount = findBankAccount(statement);
        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        }

        return bankAccount;
    }

    /**
     * @see DAO#update(AbstractEntity)
     */
    @Override
    public BankAccount update(BankAccount bankAccount) throws DAOException {

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

    /**
     * @see DAO#delete(AbstractEntity)
     */
    @Override
    public void delete(BankAccount bankAccount) throws DAOException {

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

    /**
     * Method which fills Bank Account instance with properties
     *
     * @param resultSet set with DB columns
     * @return BankAccount instance
     * @throws SQLException if it's unable to take query from DB
     * @see ResultSet
     */
    private BankAccount createBankAccount(ResultSet resultSet)
            throws SQLException {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(resultSet.getInt(1));
        bankAccount.setBalance(resultSet.getBigDecimal(2));
        bankAccount.setCurrency(resultSet.getString(3));
        bankAccount.setBlocked(resultSet.getBoolean(4));

        return bankAccount;
    }

    /**
     * Method for saving Bank Account in DB
     *
     * @param statement   prepared statement {@link  PreparedStatement}
     * @param bankAccount Bank Account entity for saving
     * @throws SQLException if it's unable to update DB
     */
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

    /**
     * Method for find all Bank Accounts in DB by provided id
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @return List of Bank Accounts with provided id field
     * @throws SQLException if it's unable to take query from DB
     */
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

    /**
     * Method for creating bank account
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @return created BankAccount
     * @throws SQLException if it's unable to take data from DB
     */
    private BankAccount findBankAccount(PreparedStatement statement) throws SQLException {

        try (statement; ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {

                return createBankAccount(resultSet);
            }
        }

        return null;
    }

    /**
     * Method for updating BankAccount
     *
     * @param statement   prepared statement {@link  PreparedStatement}
     * @param bankAccount BankAccount entity to update
     * @throws SQLException if it's unable to update DB
     */
    private void updateBankAccount(PreparedStatement statement, BankAccount bankAccount) throws SQLException {
        statement.setBigDecimal(1, bankAccount.getBalance());
        statement.setString(2, bankAccount.getCurrency());
        statement.setBoolean(3, bankAccount.isBlocked());
        statement.setInt(4, bankAccount.getId());
        statement.executeUpdate();
    }
}
