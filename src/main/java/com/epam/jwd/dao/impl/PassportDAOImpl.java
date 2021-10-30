package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.user_account.Passport;
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
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_DELETE_PASSPORT_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_FIND_ALL_PASSPORTS_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_FIND_PASSPORT_BY_ID_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_SAVE_PASSPORT_DATA_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_UPDATE_PASSPORT_QUERY;

public class PassportDAOImpl implements DAO<Passport, Integer> {

    private static DAO<Passport, Integer> instance;

    private final ConnectionPool connectionPool;

    private static final Logger log = LogManager.getLogger(PassportDAOImpl.class);

    static {
        instance = new PassportDAOImpl();
    }

    private PassportDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    public static DAO<Passport, Integer> getInstance() {
        synchronized (PassportDAOImpl.class) {
            if (instance == null) {
                instance = new PassportDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public Passport save(Passport passport)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SAVE_PASSPORT_DATA_QUERY);
            passport = savePassport(statement, passport);

        } catch (SQLException exception) {
            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return passport;
    }

    @Override
    public List<Passport> findAll()
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;
        List<Passport> passports;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL_PASSPORTS_QUERY);

            passports = findPassports(statement);
        } catch (SQLException exception) {
            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return passports;
    }



    @Override
    public Passport findById(Integer id)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;
        Passport passport = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_PASSPORT_BY_ID_QUERY);
            statement.setInt(1, id);

            passport = findPassport(statement);
        } catch (SQLException exception) {
            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        }
        finally {
            connectionPool.returnConnection(connection);
        }

        return passport;
    }

    @Override
    public Passport update(Passport passport)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_PASSPORT_QUERY);

            updatePassport(statement, passport);
        } catch (SQLException exception) {
            log.error(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
            throw new DAOException(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return passport;
    }

    @Override
    public void delete(Passport passport)
            throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_PASSPORT_QUERY);
            statement.setInt(1, passport.getId());
            statement.executeUpdate();

        } catch (SQLException exception) {
            log.error(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
            throw new DAOException(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private Passport savePassport(PreparedStatement statement, Passport passport)
            throws SQLException {

        statement.setString(1, passport.getSeriaAndNumber());
        statement.setString(2, passport.getPersonalNumber());
        statement.setDate(3, Date.valueOf(passport.getExpirationDate()));

        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();

        if(resultSet.next()) {
            passport.setId(resultSet.getInt(1));
        }

        return passport;
    }

    private List<Passport> findPassports(PreparedStatement statement)
            throws SQLException {

        ResultSet resultSet = statement.executeQuery();
        List<Passport> passports = new ArrayList<>();

        while(resultSet.next()) {
            passports.add(createPassport(resultSet));
        }

        return passports;
    }

    private Passport createPassport(ResultSet resultSet)
            throws SQLException {
        Passport passport = new Passport();
        passport.setId(resultSet.getInt(1));
        passport.setSeriaAndNumber(resultSet.getString(2));
        passport.setPersonalNumber(resultSet.getString(3));
        passport.setExpirationDate(resultSet.getDate(4).toLocalDate());

        return passport;
    }

    private Passport findPassport(PreparedStatement statement)
            throws SQLException {

        ResultSet resultSet = statement.executeQuery();
        Passport passport = null;

        if(resultSet.next()) {
            passport = createPassport(resultSet);
        }

        return passport;
    }

    private void updatePassport(PreparedStatement statement, Passport passport) throws SQLException {

        statement.setString(1, passport.getSeriaAndNumber());
        statement.setString(2, passport.getPersonalNumber());
        statement.setDate(3, Date.valueOf(passport.getExpirationDate()));
        statement.setInt(4, passport.getId());

        statement.executeUpdate();
    }
}
