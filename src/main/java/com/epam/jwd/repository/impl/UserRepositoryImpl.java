package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.Repository;
import com.epam.jwd.repository.api.ConnectionPool;
import com.epam.jwd.repository.entity.Client;
import com.epam.jwd.repository.entity.Gender;
import com.epam.jwd.repository.entity.PassportData;
import com.epam.jwd.repository.entity.User;
import com.epam.jwd.repository.exception.DeleteFromDataBaseException;
import com.epam.jwd.repository.exception.FindInDataBaseException;
import com.epam.jwd.repository.exception.SaveOperationException;
import com.epam.jwd.repository.exception.UpdateDataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements Repository<User, Integer> {

    private static Repository<User, Integer> instance = new UserRepositoryImpl();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private final Repository<PassportData, Integer> passportRepository = PassportRepositoryImpl.getInstance();

    private static final String SQL_SAVE_USER_QUERY = "INSERT INTO user ( first_name, second_name, phone_number" +
            "age, gender, client_id, role_id, passport_data_passport_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM user";
    private static final String SQL_FIND_USER_BY_ID_QUERY = "SELECT * FROM user WHERE user_id=?";
    private static final String SQL_DELETE_USER_QUERY = "DELETE FROM user WHERE user_id=?";
    private static final String SQL_USER_UPDATE_QUERY = "UPDATE user SET first_name=? second_name=? phone_number=? age =?" +
            " WHERE user_id = ?";
    private static final String INTERRUPTED_EXCEPTION_LOG_MESSAGE = "Thread was interrupted";
    private static final String SAVE_OPERATION_EXCEPTION_MESSAGE = "User wasn't save correctly";
    private static final String FIND_OPERATION_EXCEPTION_MESSAGE = "Can't find users in database";
    private static final String UPDATE_DATABASE_EXCEPTION = "Can't update user";
    private static final String FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE = "Can't find user with such id in database";
    private static final String DELETE_USER_EXCEPTION_MESSAGE = "Can't delete user from database";
    private static final Logger log = LogManager.getLogger(UserRepositoryImpl.class);

    private UserRepositoryImpl() {
    }

    public static Repository<User, Integer> getInstance() {
        synchronized (UserRepositoryImpl.class) {
            if(instance == null) {
                instance = new UserRepositoryImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public User save(User user) throws InterruptedException {
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_USER_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getGender().toString());
            preparedStatement.setInt(6, user.getClient().getId());
            preparedStatement.setInt(7, user.getRole().getRoleId());
            preparedStatement.setInt(8, user.getPassport().getId());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            log.error(SAVE_OPERATION_EXCEPTION_MESSAGE);
            throw new SaveOperationException(SAVE_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> findAll() throws InterruptedException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                User user = new User.Builder()
                        .withId(resultSet.getInt(1))
                        .withFirstName(resultSet.getString(2))
                        .withSecondName(resultSet.getString(3))
                        .withPhoneNumber(resultSet.getString(4))
                        .withAge(resultSet.getInt(5))
                        .withGender(Gender.valueOf(resultSet.getString(6).toUpperCase()))
                        .withClient(new Client())
                        .withRole()
                        .withPassport()
                        .withClient()
                        .withCreditCard()
                        .build();
                users.add(user);
            }
        } catch (SQLException exception) {
            log.error(FIND_OPERATION_EXCEPTION_MESSAGE);
            throw new FindInDataBaseException(FIND_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return users;
    }

    @Override
    public User findById(Integer id) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_FIND_USER_BY_ID_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return new User.Builder()
                        .withId(resultSet.getInt(1))
                        .withFirstName(resultSet.getString(2))
                        .withSecondName(resultSet.getString(3))
                        .withPhoneNumber(resultSet.getString(4))
                        .withAge(resultSet.getInt(5))
                        .withGender(Gender.valueOf(resultSet.getString(6).toUpperCase()))
                        .withClient(new Client())
                        .withRole()
                        .withPassport()
                        .withClient()
                        .withCreditCard()
                        .build();
            }

        } catch (SQLException exception) {
            log.error(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE);
            throw new FindInDataBaseException(FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return null;
    }

    @Override
    public User update(User user) throws InterruptedException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_USER_UPDATE_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getPhoneNumber());
            statement.setInt(4, user.getAge());
            statement.setInt(5, user.getId());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            log.error(UPDATE_DATABASE_EXCEPTION);
            throw new UpdateDataBaseException(UPDATE_DATABASE_EXCEPTION);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(INTERRUPTED_EXCEPTION_LOG_MESSAGE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return user;
    }

    @Override
    public void delete(User user) throws InterruptedException {
        Connection connection = null;
        PreparedStatement  statement;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_USER_QUERY);
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            log.error(DELETE_USER_EXCEPTION_MESSAGE);
            throw new DeleteFromDataBaseException(DELETE_USER_EXCEPTION_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
