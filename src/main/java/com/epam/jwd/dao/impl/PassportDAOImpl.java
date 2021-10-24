package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.PassportDAO;
import com.epam.jwd.dao.entity.PassportData;
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

import static com.epam.jwd.dao.messages.ExceptionMessage.DELETE_ENTITY_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.DISABLE_AUTOCOMMIT_FLAG;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.FIND_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_OPERATION_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SQL_ROLLBACK_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_DATABASE_EXCEPTION_MESSAGE;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_DELETE_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_FIND_ALL_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_FIND_BY_ID_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_FIND_PASSPORT_BY_USER_ID_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_INSERT_QUERY;
import static com.epam.jwd.dao.messages.PassportDAOMessage.SQL_UPDATE_QUERY;

public class PassportDAOImpl implements PassportDAO {

    private static PassportDAO instance;

    private final ConnectionPool connectionPool;

    private static final Logger log = LogManager.getLogger(PassportDAOImpl.class);

    static {
        instance = new PassportDAOImpl();
    }

    private PassportDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    public static PassportDAO getInstance() {
        synchronized (PassportDAOImpl.class) {
            if (instance == null) {
                instance = new PassportDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public PassportData save(PassportData passport) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setString(1, passport.getSeriesAndNumber());
            statement.setString(2, passport.getPersonalNumber());
            statement.setDate(3, Date.valueOf(passport.getExpirationTime()));
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                passport.setId(resultSet.getInt(1));
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

        return passport;
    }

    @Override
    public List<PassportData> findAll() throws InterruptedException {
        List<PassportData> passportData = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PassportData passport = createPassport(resultSet);
                passportData.add(passport);
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

        return passportData;
    }

    @Override
    public PassportData findById(Integer id) throws InterruptedException {
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

                return createPassport(resultSet);
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
    public PassportData update(PassportData passport) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_UPDATE_QUERY);
            statement.setString(1, passport.getSeriesAndNumber());
            statement.setString(2, passport.getPersonalNumber());
            statement.setDate(3, java.sql.Date.valueOf(passport.getExpirationTime()));
            statement.setInt(4, passport.getId());
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

        return passport;
    }

    @Override
    public void delete(PassportData passport) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_DELETE_QUERY);
            statement.setInt(1, passport.getId());
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
    public PassportData findPassportByUserId(Integer id) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(DISABLE_AUTOCOMMIT_FLAG);
            statement = connection.prepareStatement(SQL_FIND_PASSPORT_BY_USER_ID_QUERY);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return createPassport(resultSet);
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

    private PassportData createPassport(ResultSet resultSet)
            throws SQLException {

        PassportData passport = new PassportData();
        passport.setId(resultSet.getInt(1));
        passport.setSeriesAndNumber(resultSet.getString(2));
        passport.setPersonalNumber(resultSet.getString(3));
        passport.setExpirationTime(resultSet.getDate(4).toLocalDate());

        return passport;
    }
}
