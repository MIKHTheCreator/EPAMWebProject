package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.UserDAO;
import com.epam.jwd.dao.entity.user_account.Gender;
import com.epam.jwd.dao.entity.user_account.Role;
import com.epam.jwd.dao.entity.user_account.User;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.connection_pool_inicializer.ConnectionPoolInitializer;
import com.epam.jwd.service.exception.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDAOImplTest {

    private UserDAO<User, Integer> userDAO;
    private ConnectionPoolInitializer initializer;
    private static final User USER_TEST = new User.Builder()
            .withId(9999)
            .withFirstName("TEST")
            .withSecondName("TEST")
            .withPhoneNumber("333333333333")
            .withRole(Role.USER)
            .withGender(Gender.MALE)
            .withClientId(9999)
            .withAge(1000)
            .withPassportId(2)
            .build();
    private static final User USER_TEST_WITHOUT_PASSPORT = new User.Builder()
            .withId(9999)
            .withFirstName("TEST")
            .withSecondName("TEST")
            .withPhoneNumber("333333333333")
            .withRole(Role.USER)
            .withGender(Gender.MALE)
            .withClientId(9999)
            .withAge(1000)
            .build();
    private static final Integer ID = 9999;

    @BeforeAll
    public void beforeAll() throws ServiceException, DAOException {
        this.userDAO = UserDAOImpl.getInstance();
        this.initializer = ConnectionPoolInitializer.getInstance();
        initializer.initPool();
    }

    @AfterAll
    public void afterAll() throws ServiceException {
        initializer.shutDown();
    }


    @Test
    void findAllNotThrowsException() throws DAOException {
        assertDoesNotThrow(() -> userDAO.findAll());
        assertTrue(userDAO.findAll().contains(USER_TEST_WITHOUT_PASSPORT));
    }

    @Test
    void findByIdNotThrowsException() {
        assertDoesNotThrow(() -> userDAO.findById(ID));
    }

    @Test
    void findByIdValidUser() throws DAOException {
        assertEquals(USER_TEST_WITHOUT_PASSPORT, userDAO.findById(ID));
    }

    @Test
    void updateUserNotThrowsException() {
        assertDoesNotThrow(() -> userDAO.update(USER_TEST));
    }

    @Test
    void findUserByClientId() throws DAOException {
        assertEquals(USER_TEST_WITHOUT_PASSPORT, userDAO.findUserByClientId(ID));
    }

    @Test
    void updateUsersPassportId() {
        assertDoesNotThrow(() -> userDAO.updateUsersPassportId(USER_TEST));
    }
}