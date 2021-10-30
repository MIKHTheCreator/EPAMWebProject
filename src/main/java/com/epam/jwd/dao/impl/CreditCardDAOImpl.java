package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.payment_system.CreditCard;
import com.epam.jwd.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.messages.CreditCardDAOMessage.SQL_DELETE_CREDIT_CARD_QUERY;
import static com.epam.jwd.dao.messages.CreditCardDAOMessage.SQL_FIND_ALL_CREDIT_CARDS_QUERY;
import static com.epam.jwd.dao.messages.CreditCardDAOMessage.SQL_FIND_CREDIT_CARD_BY_ID_QUERY;
import static com.epam.jwd.dao.messages.CreditCardDAOMessage.SQL_SAVE_CREDIT_CARD_QUERY;
import static com.epam.jwd.dao.messages.CreditCardDAOMessage.SQL_UPDATE_CREDIT_CARD_QUERY;
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


public class CreditCardDAOImpl implements DAO<CreditCard, Integer> {

    private static DAO<CreditCard, Integer> instance;

    private final ConnectionPool connectionPool;

    private static final Logger log = LogManager.getLogger(CreditCardDAOImpl.class);

    static {
        instance = new CreditCardDAOImpl();
    }

    private CreditCardDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    public static DAO<CreditCard, Integer> getInstance() {
        synchronized (CreditCardDAOImpl.class) {
            if (instance == null) {
                instance = new CreditCardDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public CreditCard save(CreditCard creditCard)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SAVE_CREDIT_CARD_QUERY);

            saveCreditCard(statement, creditCard);

        } catch (SQLException exception) {
            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCard;
    }

    @Override
    public List<CreditCard> findAll()
            throws InterruptedException, DAOException {
        List<CreditCard> creditCards;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL_CREDIT_CARDS_QUERY);

            creditCards = findCreditCards(statement);

        } catch (SQLException exception) {
            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCards;
    }

    @Override
    public CreditCard findById(Integer id)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;
        CreditCard creditCard;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_BY_ID_QUERY);
            statement.setInt(1, id);

            creditCard = findCreditCard(statement);
        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCard;
    }

    @Override
    public CreditCard update(CreditCard creditCard)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CREDIT_CARD_QUERY);
            updateCreditCard(statement, creditCard);

        } catch (SQLException exception) {
            log.error(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
            throw new DAOException(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCard;
    }

    @Override
    public void delete(CreditCard creditCard)
            throws InterruptedException, DAOException {
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CREDIT_CARD_QUERY)) {
                statement.setInt(1, creditCard.getId());
                statement.executeUpdate();
            }

        } catch (SQLException exception) {
            log.error(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
            throw new DAOException(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private CreditCard createCreditCard(ResultSet resultSet)
            throws SQLException {

        return new CreditCard.Builder().withId(resultSet.getInt(1))
                .withNumber(resultSet.getString(2))
                .withExpirationDate(resultSet.getDate(3).toLocalDate())
                .withFullName(resultSet.getString(4))
                .withCVV(resultSet.getString(5))
                .withPin(resultSet.getString(6))
                .withUserId(resultSet.getInt(7))
                .withBankAccountId(resultSet.getInt(8))
                .build();
    }

    private void saveCreditCard(PreparedStatement statement, CreditCard creditCard) throws SQLException {

        ResultSet resultSet = null;
        try {
            statement.setString(1, creditCard.getNumber());
            statement.setDate(2, Date.valueOf(creditCard.getExpirationDate()));
            statement.setString(3, creditCard.getFullName());
            statement.setString(4, creditCard.getCVV());
            statement.setString(5, creditCard.getPin());
            statement.setInt(6, creditCard.getUserId());
            statement.setInt(7, creditCard.getBankAccountId());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                creditCard.setId(resultSet.getInt(1));
            }
        } finally {
            statement.close();
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    private CreditCard findCreditCard(PreparedStatement statement) throws SQLException {
        try (statement; ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return createCreditCard(resultSet);
            }
        }

        return null;
    }

    private void updateCreditCard(PreparedStatement statement, CreditCard creditCard) throws SQLException {
        try (statement) {
            statement.setString(1, creditCard.getNumber());
            statement.setDate(2, Date.valueOf(creditCard.getExpirationDate()));
            statement.setString(3, creditCard.getFullName());
            statement.setString(4, creditCard.getCVV());
            statement.setString(5, creditCard.getPin());
            statement.setInt(6, creditCard.getUserId());
            statement.setInt(7, creditCard.getBankAccountId());
            statement.setInt(8, creditCard.getId());
            statement.executeUpdate();
        }
    }

    private List<CreditCard> findCreditCards(PreparedStatement statement) throws SQLException {
        try (statement; ResultSet resultSet = statement.executeQuery()) {

            List<CreditCard> creditCards = new ArrayList<>();

            while (resultSet.next()) {
                CreditCard creditCard = createCreditCard(resultSet);

                creditCards.add(creditCard);
            }

            return creditCards;
        }
    }
}
