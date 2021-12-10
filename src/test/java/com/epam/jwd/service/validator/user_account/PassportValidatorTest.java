package com.epam.jwd.service.validator.user_account;

import com.epam.jwd.service.dto.user_account.PassportDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.EXPIRATION_DATE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.EXPIRATION_DATE_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PERSONAL_NUMBER_MISS_MATCH;
import static com.epam.jwd.service.message.ExceptionMessage.PERSONAL_NUMBER_MISS_MATCH_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERIA_AND_NUMBER_MISS_MATCH;
import static com.epam.jwd.service.message.ExceptionMessage.SERIA_AND_NUMBER_MISS_MATCH_CODE;
import static org.junit.Assert.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PassportValidatorTest {

    private Validator<PassportDTO, Integer> validator;

    private static final PassportDTO PASSPORT_WITH_WRONG_SERIA_AND_NUMBER = new PassportDTO("MP36276499", "5300701A013PB0",LocalDate.parse("2022-01-21"));
    private static final PassportDTO PASSPORT_WITH_WRONG_PERSONAL_NUMBER = new PassportDTO("MP3627649", "53A00701A013PB0",LocalDate.parse("2022-01-21"));
    private static final PassportDTO PASSPORT_WITH_WRONG_EXPIRATION_DATE = new PassportDTO("MP3627649", "5300701A013PB0",LocalDate.parse("2019-01-21"));

    @BeforeAll
    public void beforeAll() {
        this.validator = PassportValidator.getInstance();
    }

    @Test
    void validateForWrongSeriaAndNumber() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(PASSPORT_WITH_WRONG_SERIA_AND_NUMBER));

        Assertions.assertEquals(SERIA_AND_NUMBER_MISS_MATCH + DELIMITER + SERIA_AND_NUMBER_MISS_MATCH_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongPersonalNumber() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(PASSPORT_WITH_WRONG_PERSONAL_NUMBER));

        Assertions.assertEquals(PERSONAL_NUMBER_MISS_MATCH + DELIMITER + PERSONAL_NUMBER_MISS_MATCH_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongPhoneNumber() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(PASSPORT_WITH_WRONG_EXPIRATION_DATE));

        Assertions.assertEquals(EXPIRATION_DATE_EXCEPTION + DELIMITER + EXPIRATION_DATE_EXCEPTION_CODE,
                exception.getMessage());
    }
}