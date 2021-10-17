package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.Repository;
import com.epam.jwd.repository.api.ConnectionPool;
import com.epam.jwd.repository.entity.PassportData;
import com.epam.jwd.repository.exception.FindDataBaseException;
import com.epam.jwd.repository.exception.SaveOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PassportRepositoryImpl implements Repository<PassportData, Integer> {

    private static Repository<PassportData, Integer> instance = new PassportRepositoryImpl();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    private static final String SQL_INSERT_QUERY = "INSERT (seria_and_number, personal_number, expiration_date) INTO passport_data" +
            "VALUES (?, ?, ?)";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM passport_data";
    private static final String SQL_INSERT_EXCEPTION_MESSAGE = "Insert passport data to database was failed";
    private static final String SQL_FIND_ALL_EXCEPTION_MESSAGE = "Selecting passport data info from database was failed";

    private static final Logger log = LogManager.getLogger(PassportRepositoryImpl.class);

    private PassportRepositoryImpl() {
    }

    public Repository<PassportData, Integer> getInstance() {
        synchronized (PassportRepositoryImpl.class) {
            if(instance == null) {
                instance = new PassportRepositoryImpl();
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
            //todo rebuild without using Date
            statement.setDate(3, (java.sql.Date) new Date(String.valueOf(passport.getExpirationTime())));
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                passport.setId(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            log.error(SQL_INSERT_EXCEPTION_MESSAGE);
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
            log.error(SQL_FIND_ALL_EXCEPTION_MESSAGE);
            throw new FindDataBaseException(SQL_FIND_ALL_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return passportData;
    }

    @Override
    public PassportData findById(Integer id) throws InterruptedException {
        return null;
    }

    @Override
    public PassportData update(PassportData passport) throws InterruptedException {
        return null;
    }

    @Override
    public void delete(PassportData passport) throws InterruptedException {

    }
}
