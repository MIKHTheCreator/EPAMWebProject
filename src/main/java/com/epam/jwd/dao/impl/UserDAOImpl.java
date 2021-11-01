package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.user_account.Gender;
import com.epam.jwd.dao.entity.user_account.User;
import com.epam.jwd.dao.entity.user_account.Role;
import com.epam.jwd.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
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
import static com.epam.jwd.dao.messages.ExceptionMessage.ROLLBACK_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.ROLLBACK_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.SAVE_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_EXCEPTION;
import static com.epam.jwd.dao.messages.ExceptionMessage.UPDATE_EXCEPTION_CODE;
import static com.epam.jwd.dao.messages.UserDAOMessage.SQL_DELETE_USER_QUERY;
import static com.epam.jwd.dao.messages.UserDAOMessage.SQL_FIND_ALL_USERS_QUERY;
import static com.epam.jwd.dao.messages.UserDAOMessage.SQL_FIND_ROLE_BY_ID;
import static com.epam.jwd.dao.messages.UserDAOMessage.SQL_FIND_USER_BY_ID_QUERY;
import static com.epam.jwd.dao.messages.UserDAOMessage.SQL_SAVE_USER_QUERY;
import static com.epam.jwd.dao.messages.UserDAOMessage.SQL_UPDATE_USER_QUERY;

public class UserDAOImpl implements DAO<User, Integer> {

    private static DAO<User, Integer> instance;
    private final ConnectionPool connectionPool;

    private static final Logger log = LogManager.getLogger(UserDAOImpl.class);

    static {
        instance = new UserDAOImpl();
    }

    private UserDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    public static DAO<User, Integer> getInstance() {
        synchronized (UserDAOImpl.class) {
            if (instance == null) {
                instance = new UserDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    @Override
    public User save(User user)
            throws InterruptedException, DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_SAVE_USER_QUERY);

            saveUser(statement, user);
        } catch (SQLException exception) {
            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        }

        return user;
    }


    @Override
    public List<User> findAll() throws InterruptedException, DAOException {
        List<User> users;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(SQL_FIND_ALL_USERS_QUERY);

            users = findUsers(statement, connection);
            connection.commit();
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
                throw new DAOException(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
            }

            log.error(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_ALL_EXCEPTION + DELIMITER + FIND_ALL_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return users;
    }

    @Override
    public User findById(Integer id) throws InterruptedException, DAOException {
        Connection connection = null;
        PreparedStatement statement;
        User user;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(SQL_FIND_USER_BY_ID_QUERY);
            statement.setInt(1, id);

            user = findUser(statement, connection);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
                throw new DAOException(ROLLBACK_EXCEPTION + DELIMITER + ROLLBACK_EXCEPTION_CODE, e);
            }

            log.error(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
            throw new DAOException(FIND_BY_ID_EXCEPTION + DELIMITER + FIND_BY_ID_EXCEPTION_CODE, exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return user;
    }

    @Override
    public User update(User user) throws InterruptedException, DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_UPDATE_USER_QUERY);

            updateUser(statement, user);
        } catch (SQLException exception) {
            log.error(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
            throw new DAOException(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
        }

        return user;
    }

    @Override
    public void delete(User user) throws InterruptedException, DAOException {

        try (Connection connection = connectionPool.takeConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_QUERY)) {
                statement.setInt(1, user.getId());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            log.error(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
            throw new DAOException(DELETE_EXCEPTION + DELIMITER + DELETE_EXCEPTION_CODE, exception);
        }
    }

    private void saveUser(PreparedStatement statement, User user)
            throws SQLException {

        ResultSet resultSet = null;

        try {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getPhoneNumber());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getGender().toString());
            statement.setInt(6, user.getClientId());
            statement.setInt(7, user.getPassportId());
            statement.setInt(8, user.getRole().getRoleId());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
        } finally {
            statement.close();
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    private Role findRoleById(Connection connection, Integer id)
            throws SQLException {
        PreparedStatement statement;


        statement = connection.prepareStatement(SQL_FIND_ROLE_BY_ID);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Role.valueOf(resultSet.getString(2).toUpperCase());
        }

        return Role.USER;
    }

    private User createUser(ResultSet resultSet, Connection connection)
            throws SQLException {
        return new User.Builder()
                .withId(resultSet.getInt(1))
                .withFirstName(resultSet.getString(2))
                .withSecondName(resultSet.getString(3))
                .withPhoneNumber(resultSet.getString(4))
                .withAge(resultSet.getInt(5))
                .withGender(Gender.valueOf(resultSet.getString(6).toUpperCase()))
                .withClientId(resultSet.getInt(7))
                .withPassportId(resultSet.getInt(8))
                .withRole(findRoleById(connection, resultSet.getInt(9)))
                .build();
    }

    private List<User> findUsers(PreparedStatement statement, Connection connection) throws SQLException {
        try (statement; ResultSet resultSet = statement.executeQuery()) {

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                User user = createUser(resultSet, connection);
                users.add(user);
            }

            return users;
        }
    }

    private User findUser(PreparedStatement statement, Connection connection) throws SQLException {
        try (statement; ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return createUser(resultSet, connection);
            }
        }

        return null;
    }

    private void updateUser(PreparedStatement statement, User user) throws SQLException {
        try (statement) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getPhoneNumber());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getGender().toString());
            statement.setInt(6, user.getClientId());
            statement.setInt(7, user.getPassportId());
            statement.setInt(8, user.getRole().getRoleId());
            statement.setInt(9, user.getId());
        }
    }
}
