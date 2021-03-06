package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.UserDAO;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.entity.AbstractEntity;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.message.ExceptionMessage.DELETE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.DELETE_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_ALL_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_ALL_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_BY_ID_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.FIND_BY_ID_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.ROLLBACK_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.ROLLBACK_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.SAVE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.SAVE_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.ExceptionMessage.UPDATE_EXCEPTION;
import static com.epam.jwd.dao.message.ExceptionMessage.UPDATE_EXCEPTION_CODE;
import static com.epam.jwd.dao.message.UserDAOMessage.*;

/**
 * User DAO implementation of UserDAO for User entity with Integer id
 *
 * @see UserDAO
 */
public class UserDAOImpl implements UserDAO<User, Integer> {

    private static UserDAO<User, Integer> instance;
    private final ConnectionPool connectionPool;

    private static final Logger log = LogManager.getLogger(UserDAOImpl.class);

    static {
        instance = new UserDAOImpl();
    }

    private UserDAOImpl() {
        this.connectionPool = ConnectionPoolImpl.getInstance();
    }

    public static UserDAO<User, Integer> getInstance() {
        synchronized (UserDAOImpl.class) {
            if (instance == null) {
                instance = new UserDAOImpl();
                return instance;
            }
        }

        return instance;
    }

    /**
     * @see UserDAO#save(AbstractEntity)
     */
    @Override
    public User save(User user)
            throws DAOException {

        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_SAVE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            saveUser(statement, user);
        } catch (SQLException exception) {
            log.error(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
            throw new DAOException(SAVE_EXCEPTION + DELIMITER + SAVE_EXCEPTION_CODE, exception);
        }

        return user;
    }

    /**
     * @see UserDAO#findAll()
     */
    @Override
    public List<User> findAll() throws DAOException {
        List<User> users;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_FIND_ALL_USERS_QUERY);

            users = findUsers(statement, connection);
            connection.commit();
            connection.setAutoCommit(true);
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

    /**
     * @see UserDAO#findById(Object)
     */
    @Override
    public User findById(Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement;
        User user;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
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

    /**
     * @see UserDAO#update(AbstractEntity)
     */
    @Override
    public User update(User user) throws DAOException {

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

    /**
     * @see UserDAO#delete(AbstractEntity)
     */
    @Override
    public void delete(User user) throws DAOException {

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

    /**
     * @see UserDAO#findUserByClientId(Object)
     */
    @Override
    public User findUserByClientId(Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement;
        User user;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_FIND_USER_BY_CLIENT_ID_QUERY);
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

    /**
     * @see UserDAO#updateUsersPassportId(AbstractEntity)
     */
    @Override
    public User updateUsersPassportId(User user) throws DAOException {
        PreparedStatement statement;
        try (Connection connection = connectionPool.takeConnection()) {
            statement = connection.prepareStatement(SQL_UPDATE_PASSPORT_DATA_QUERY);

            updatePassportData(statement, user);
        } catch (SQLException exception) {
            log.error(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
            throw new DAOException(UPDATE_EXCEPTION + DELIMITER + UPDATE_EXCEPTION_CODE, exception);
        }

        return user;
    }

    /**
     * @see UserDAO#findUsersToPage(int, int)
     */
    @Override
    public List<User> findUsersToPage(int page, int numOfPositions) throws DAOException {
        List<User> users;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_FIND_ALL_USERS_TO_PAGE_QUERY);
            statement.setInt(1, page - 1);
            statement.setInt(2, numOfPositions);

            users = findUsers(statement, connection);
            connection.commit();
            connection.setAutoCommit(true);
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

    /**
     * Method for saving User in DB
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @param user      User Data entity for saving
     * @throws SQLException if it's unable to update DB
     */
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
            statement.setInt(7, user.getRole().getRoleId());
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

    /**
     * Method for extracting Role data from DB with provided id
     *
     * @param connection connection to DB
     * @param id         role id
     * @return founded role
     * @throws SQLException if can't execute query
     */
    private Role findRoleById(Connection connection, Integer id)
            throws SQLException {
        PreparedStatement statement;


        statement = connection.prepareStatement(SQL_FIND_ROLE_BY_ID);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Role.valueOf(resultSet.getString(1).toUpperCase());
        }

        return Role.USER;
    }

    /**
     * Method for creating User entity with extracted fields
     *
     * @param resultSet query with different columns
     * @return Created User with generated id
     * @throws SQLException if it's unable to take data from query
     * @see ResultSet
     */
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
                .withRole(findRoleById(connection, resultSet.getInt(9)))
                .build();
    }

    /**
     * Method for find all Users in DB by provided id
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @return List of Users with provided id field
     * @throws SQLException if it's unable to take query from DB
     */
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

    /**
     * Method for creating User
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @return created User
     * @throws SQLException if it's unable to take data from DB
     */
    private User findUser(PreparedStatement statement, Connection connection) throws SQLException {
        try (statement; ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return createUser(resultSet, connection);
            }
        }

        return null;
    }

    /**
     * Method for updating User
     *
     * @param statement prepared statement {@link  PreparedStatement}
     * @param user      User entity to update
     * @throws SQLException if it's unable to update DB
     */
    private void updateUser(PreparedStatement statement, User user) throws SQLException {
        try (statement) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getPhoneNumber());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getGender().toString());
            statement.setInt(6, user.getId());
            statement.executeUpdate();
        }
    }

    /**
     * Method for updating User's passport field
     *
     * @param statement prepared statement {@link PreparedStatement}
     * @param user      User with different passport id
     * @throws SQLException if it's unavailable to execute update
     */
    private void updatePassportData(PreparedStatement statement, User user) throws SQLException {
        try (statement) {
            statement.setInt(1, user.getPassportId());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        }
    }
}
