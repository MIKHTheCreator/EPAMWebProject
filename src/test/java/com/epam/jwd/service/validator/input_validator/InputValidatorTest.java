package com.epam.jwd.service.validator.input_validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InputValidatorTest {

    private InputValidator validator;
    private static final Integer ZERO_AGE = 0;
    private static final Integer NON_ZERO_AGE = 5;
    private static final String VALID_AGE = "25";
    private static final String INVALID_AGE = "-25";
    private static final String EMPTY_STRING = "";
    private static final String NOT_EMPTY_STRING = "1";

    @BeforeAll
    public void beforeAll() {
        this.validator = InputValidator.getInstance();
    }

    @Test
    void isValidAgeFormat() {
        assertTrue(validator.isValidAgeFormat(VALID_AGE));
    }

    @Test
    void isNotValidAgeFormat() {
        assertFalse(validator.isValidAgeFormat(INVALID_AGE));
    }

    @Test
    void isEmptyString() {
        assertTrue(validator.isEmptyString(EMPTY_STRING));
    }

    @Test
    void isNotEmptyString() {
        assertFalse(validator.isEmptyString(NOT_EMPTY_STRING));
    }

    @Test
    void isZeroField() {
        assertTrue(validator.isZeroField(ZERO_AGE));
    }

    @Test
    void isNotZeroField() {
        assertFalse(validator.isZeroField(NON_ZERO_AGE));
    }
}