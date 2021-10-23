package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.entity.PassportData;
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

public class PassportDAOImpl implements DAO<PassportData, Integer> {

    private static DAO<PassportData, Integer> instance = new PassportDAOImpl();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    private static final String SQL_INSERT_QUERY = "INSERT INTO passport_data (seria_and_number, personal_number, expiration_date)" +
            "VALUES (?, ?, ?)";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM passport_data";
    private static final String SQL_FIND_BY_ID_QUERY = "SELECT * FROM passport_data WHERE passport_id=?";
    private static final String SQL_UPDATE_QUERY = "UPDATE passport_data SET seria_and_number=? personal_number=? expiration_date=? WHERE passport_id=?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM passport_data WHERE passport_id=?";
    private static final String SQL_INSERT_EXCEPTION_MESSAGE = "Insert passport data to database was failed";
    private static final String SQL_FIND_ALL_EXCEPTION_MESSAGE = "Selecting passport data info from database was failed";
    private static final String SQL_FIND_BY_ID_EXCEPTION_MESSAGE = "There is no Passport with such id in database";
    private static final String SQL_UPDATE_EXCEPTION_MESSAGE = "Updating passport information was failed";
    private static final String SQL_DELETE_EXCEPTION_MESSAGE = "Deleting passport with such id was failed";
    private static final Logger log = LogManager.getLogger(PassportDAOImpl.class);

    private PassportDAOImpl() {
    }

    public static DAO<PassportData, Integer> getInstance() {
        synchronized (PassportDAOImpl.class) {
            if(instance == null) {
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
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setString(1, passport.getSeriesAndNumber());
            statement.setString(2, passport.getPersonalNumber());
            statement.setDate(3, Date.valueOf(passport.getExpirationTime()));
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                passport.setId(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            log.error(SQL_INSERT_EXCEPTION_MESSAGE, exception);
            throw new SaveOperationException(SQL_INSERT_EXCEPTION_MESSAGE);
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
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PassportData passport = new PassportData();
                passport.setId(resultSet.getInt(1));
                passport.setSeriesAndNumber(resultSet.getString(2));
                passport.setPersonalNumber(resultSet.getString(3));
                passport.setExpirationTime(resultSet.getDate(4).toLocalDate());
                passportData.add(passport);
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_ALL_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_ALL_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return passportData;
    }

    @Override
    public PassportData findById(Integer id) throws InterruptedException {
        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_BY_ID_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                PassportData passport = new PassportData();
                passport.setId(id);
                passport.setSeriesAndNumber(resultSet.getString(2));
                passport.setPersonalNumber(resultSet.getString(3));
                passport.setExpirationTime(resultSet.getDate(4).toLocalDate());

                return passport;
            }
        } catch (SQLException exception) {
            log.error(SQL_FIND_BY_ID_EXCEPTION_MESSAGE, exception);
            throw new FindInDataBaseException(SQL_FIND_BY_ID_EXCEPTION_MESSAGE);
        }

        return null;
    }

    @Override
    public PassportData update(PassportData passport) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_QUERY);
            statement.setString(1, passport.getSeriesAndNumber());
            statement.setString(2, passport.getPersonalNumber());
            statement.setDate(3, java.sql.Date.valueOf(passport.getExpirationTime()));
            statement.setInt(4, passport.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(SQL_UPDATE_EXCEPTION_MESSAGE, exception);
            throw new UpdateDataBaseException(SQL_UPDATE_EXCEPTION_MESSAGE);
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
            statement = connection.prepareStatement(SQL_DELETE_QUERY);
            statement.setInt(1, passport.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(SQL_DELETE_EXCEPTION_MESSAGE, exception);
            throw new DeleteFromDataBaseException(SQL_DELETE_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
