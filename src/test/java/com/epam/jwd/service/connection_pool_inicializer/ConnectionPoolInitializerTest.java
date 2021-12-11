package com.epam.jwd.service.connection_pool_inicializer;

import com.epam.jwd.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConnectionPoolInitializerTest {

    private ConnectionPoolInitializer initializer;

    @BeforeAll
    public void beforeAll() {
        this.initializer = ConnectionPoolInitializer.getInstance();
    }

    @Test
    void initPool() {
        assertDoesNotThrow(() -> initializer.initPool());
    }

    @Test
    void shutDown() {
        assertDoesNotThrow(() -> initializer.shutDown());
    }
}