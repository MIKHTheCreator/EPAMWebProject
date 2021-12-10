package com.epam.jwd.service.validator.user_account;

import com.epam.jwd.service.dto.user_account.ClientDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.UNSUPPORTED_EMAIL;
import static com.epam.jwd.service.message.ExceptionMessage.UNSUPPORTED_EMAIL_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION_CODE;
import static org.junit.Assert.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientValidatorTest {

    private Validator<ClientDTO, Integer> validator;
    private static final ClientDTO CLIENT_WITH_WRONG_USERNAME = new ClientDTO("302", "antianti@tut.by", "Antonio1990");
    private static final ClientDTO CLIENT_WITH_WRONG_EMAIL = new ClientDTO("Mikhail_30", "antianti.tut.by", "Antonio1990");
    private static final ClientDTO CLIENT_WITH_WRONG_PASSWORD = new ClientDTO("Mikhail_30", "antianti@tut.by", "qwerty1234567");

    @BeforeAll
    public void beforeAll() {
        this.validator = ClientValidator.getInstance();
    }

    @Test
    void validateForWrongUsername() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(CLIENT_WITH_WRONG_USERNAME));

        Assertions.assertEquals(USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION + DELIMITER + USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongEmail() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(CLIENT_WITH_WRONG_EMAIL));

        Assertions.assertEquals(UNSUPPORTED_EMAIL + DELIMITER + UNSUPPORTED_EMAIL_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongPassword() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(CLIENT_WITH_WRONG_PASSWORD));

        Assertions.assertEquals(PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION + DELIMITER + PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION_CODE,
                exception.getMessage());
    }
}