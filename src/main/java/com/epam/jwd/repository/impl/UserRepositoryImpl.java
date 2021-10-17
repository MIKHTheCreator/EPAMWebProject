package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.Repository;
import com.epam.jwd.repository.api.ConnectionPool;
import com.epam.jwd.repository.entity.User;
import com.epam.jwd.repository.exception.SaveOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements Repository<User, Integer> {

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    private static final String SQL_SAVE_USER_QUERY = "INSERT INTO user (user_id, first_name, second_name, phone_number" +
            "age, gender, client_id, role_id, passport_data_passport_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INTERRUPTED_EXCEPTION_LOG_MESSAGE = "Thread was interrupted";
    private static final String SAVE_OPERATION_EXCEPTION_MESSAGE = "User wasn't save correctly";

    private static final Logger log = LogManager.getLogger(UserRepositoryImpl.class);

    @Override
    public User save(User user) throws InterruptedException {
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_USER_QUERY);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setInt(5, user.getAge());
            preparedStatement.setString(6, user.getRole().toString());
            preparedStatement.setInt(7, user.getClient().getId());
            preparedStatement.setInt(8, user.getRole().getRoleId());
            preparedStatement.setInt(9, user.getPassport().getId());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            throw new SaveOperationException(SAVE_OPERATION_EXCEPTION_MESSAGE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(INTERRUPTED_EXCEPTION_LOG_MESSAGE);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(User entity) {

    }
}
