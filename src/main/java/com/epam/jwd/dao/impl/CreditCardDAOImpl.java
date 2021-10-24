package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.BankAccountDAO;
import com.epam.jwd.dao.api.CreditCardDAO;
import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.entity.CreditCard;
import com.epam.jwd.dao.exception.DeleteFromDataBaseException;
import com.epam.jwd.dao.exception.FindInDataBaseException;
import com.epam.jwd.dao.exception.RollBackOperationException;
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

public class CreditCardDAOImpl implements CreditCardDAO {

    private static CreditCardDAO instance;

    private final ConnectionPool connectionPool;
    private final BankAccountDAO bankAccountDAO;

    private static final String SQL_INSERT_QUERY = "INSERT INTO credit_card (credit_card_number, credit_card_expiration_date," +
            " name_and_surname, cvv, password, user_id, bank_account_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM credit_card";
    private static final String SQL_FIND_BY_ID_QUERY = "SELECT * FROM credit_card WHERE credit_card_id=?";
    private static final String SQL_UPDATE_QUERY = "UPDATE credit_card SET credit_card_number=? credit_card_expiration=?" +
            " name_and_surname=? cvv=? password=? user_id=? WHERE credit_card_id=?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM credit_card WHERE credit_card_id=?";
    private static final String SQL_FIND_ALL_CREDIT_CARDS_BY_USER_ID = "SELECT * FROM credit_card WHERE user_id=?";
    private static final String SQL_INSERT_EXCEPTION_MESSAGE = "Insert credit card data to database was failed";
    private static final String SQL_FIND_ALL_EXCEPTION_MESSAGE = "Selecting credit card data info from database was failed";
    private static final String SQL_FIND_BY_ID_EXCEPTION_MESSAGE = "There is no credit card with such id in database";
    private static final String SQL_UPDATE_EXCEPTION_MESSAGE = "Updating credit_card information was failed";
    private static final String SQL_DELETE_EXCEPTION_MESSAGE = "Deleting credit card with such id was failed";
    private static final String SQL_ROLLBACK_EXCEPTION_MESSAGE = "Can't rollback to the beginning state";
    private static final boolean DISABLE_AUTOCOMMIT_FLAG = false;
    private static final Logger log = LogManager.getLogger(CreditCardDAOImpl.class);

    static {
        instance = new CreditCardDAOImpl();
    }

    private CreditCardDAOImpl() {
        this.bankAccountDAO = BankAccountDAOImpl.getInstance();
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    public static CreditCardDAO getInstance() {
        synchronized (CreditCardDAOImpl.class) {
            if (instance == null) {
                instance = new CreditCardDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public CreditCard save(CreditCard creditCard) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setInt(1, creditCard.getCreditCardNumber());
            statement.setDate(2, Date.valueOf(creditCard.getCreditCardExpiration()));
            statement.setString(3, creditCard.getNameAndSurname());
            statement.setInt(4, creditCard.getCVV());
            statement.setInt(5, creditCard.getPassword());
            statement.setInt(6, creditCard.getUserId());
            statement.setInt(7, creditCard.getBankAccount().getId());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                creditCard.setId(resultSet.getInt(1));
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(SQL_INSERT_EXCEPTION_MESSAGE, exception);
            throw new SaveOperationException(SQL_INSERT_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCard;
    }

    @Override
    public List<CreditCard> findAll() throws InterruptedException {
        List<CreditCard> creditCards = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CreditCard creditCard = createCreditCard(resultSet);

                creditCards.add(creditCard);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(SQL_FIND_ALL_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_ALL_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCards;
    }

    @Override
    public CreditCard findById(Integer id) throws InterruptedException {
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
                return createCreditCard(resultSet);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(SQL_FIND_BY_ID_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_BY_ID_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
    }

    @Override
    public CreditCard update(CreditCard creditCard) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_UPDATE_QUERY);
            statement.setInt(1, creditCard.getCreditCardNumber());
            statement.setDate(2, Date.valueOf(creditCard.getCreditCardExpiration()));
            statement.setString(3, creditCard.getNameAndSurname());
            statement.setInt(4, creditCard.getCVV());
            statement.setInt(5, creditCard.getPassword());
            statement.setInt(6, creditCard.getUserId());
            statement.setInt(7, creditCard.getId());
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(SQL_UPDATE_EXCEPTION_MESSAGE, exception);
            throw new UpdateDataBaseException(SQL_UPDATE_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCard;
    }

    @Override
    public void delete(CreditCard creditCard) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_DELETE_QUERY);
            statement.setInt(1, creditCard.getId());
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(SQL_DELETE_EXCEPTION_MESSAGE, exception);
            throw new DeleteFromDataBaseException(SQL_DELETE_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<CreditCard> findAllCreditCardsByUserId(Integer id) throws InterruptedException {
        List<CreditCard> creditCards = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_ALL_CREDIT_CARDS_BY_USER_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CreditCard creditCard = new CreditCard.Builder()
                        .withId(resultSet.getInt(1))
                        .withCreditCardNumber(resultSet.getInt(2))
                        .withCreditCardExpiration(resultSet.getDate(3).toLocalDate())
                        .withNameAndSurname(resultSet.getString(4))
                        .withCVV(resultSet.getInt(5))
                        .withPassword(resultSet.getInt(6))
                        .withUserId(id)
                        .build();

                creditCards.add(creditCard);
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(SQL_ROLLBACK_EXCEPTION_MESSAGE, e);
                throw new RollBackOperationException(SQL_ROLLBACK_EXCEPTION_MESSAGE);
            }

            log.error(SQL_FIND_ALL_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_ALL_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
    }

    private CreditCard createCreditCard(ResultSet resultSet)
            throws SQLException, InterruptedException {

        return new CreditCard.Builder().withId(resultSet.getInt(1))
                .withCreditCardNumber(resultSet.getInt(2))
                .withCreditCardExpiration(resultSet.getDate(3).toLocalDate())
                .withNameAndSurname(resultSet.getString(4))
                .withCVV(resultSet.getInt(5))
                .withPassword(resultSet.getInt(6))
                .withBankAccount(bankAccountDAO.findBankAccountByCreditCardId(resultSet.getInt(1)))
                .withUserId(resultSet.getInt(7))
                .build();
    }
}
