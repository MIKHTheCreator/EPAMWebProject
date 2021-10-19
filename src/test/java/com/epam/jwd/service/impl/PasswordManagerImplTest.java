package com.epam.jwd.service.impl;


import com.epam.jwd.service.api.PasswordManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PasswordManagerImplTest {

    private PasswordManager passwordManager;
    private String password;

    @BeforeAll
    public void beforeAll() {
        passwordManager = new PasswordManagerImpl();
        password = "qwerty1234567";
    }

    @Test
    void shouldGenerateNotSameStringAsInput() {
        assertNotEquals(passwordManager.encode(password), password);
    }

    @Test
    void encodedTwoTimesPasswordsShouldBeTheSame() {
        assertEquals(passwordManager.encode(password), passwordManager.encode(password));
    }

    @Test
    void decodedAndEncodedPasswordsShouldBeTheSame() {
        String encodedPassword = passwordManager.encode(password);
        assertEquals(password, passwordManager.decode(encodedPassword));
    }
}