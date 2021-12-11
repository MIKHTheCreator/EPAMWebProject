package com.epam.jwd.service.validator.payment_system;

import com.epam.jwd.service.dto.payment_system.CreditCardDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.CREDIT_CARD_NUMBER_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.CREDIT_CARD_NUMBER_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.CVV_CODE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.CVV_CODE_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.EXPIRATION_DATE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.EXPIRATION_DATE_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.FULL_NAME_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.FULL_NAME_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PIN_FORMAT_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PIN_FORMAT_EXCEPTION_CODE;
import static org.junit.Assert.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreditCardValidatorTest {

    private Validator<CreditCardDTO, Integer> validator;
    private static final CreditCardDTO CARD_WITH_WRONG_CARD_NUMBER = new CreditCardDTO.Builder()
            .withNumber("1234")
            .withCVV("911")
            .withPin("4444")
            .withFullName("MIKHAIL KHAREVICH")
            .withExpirationDate(LocalDate.parse("2022-12-11"))
            .build();
    private static final CreditCardDTO CARD_WITH_WRONG_CARD_CVV = new CreditCardDTO.Builder()
            .withNumber("1234123412341234")
            .withCVV("9111")
            .withPin("4444")
            .withFullName("MIKHAIL KHAREVICH")
            .withExpirationDate(LocalDate.parse("2022-12-11"))
            .build();
    private static final CreditCardDTO CARD_WITH_WRONG_CARD_PIN = new CreditCardDTO.Builder()
            .withNumber("1234123412341234")
            .withCVV("911")
            .withPin("44442")
            .withFullName("MIKHAIL KHAREVICH")
            .withExpirationDate(LocalDate.parse("2022-12-11"))
            .build();
    private static final CreditCardDTO CARD_WITH_WRONG_CARD_FULL_NAME = new CreditCardDTO.Builder()
            .withNumber("1234123412341234")
            .withCVV("911")
            .withPin("4444")
            .withFullName("MIKHAIL*KHAREVICH*")
            .withExpirationDate(LocalDate.parse("2022-12-11"))
            .build();
    private static final CreditCardDTO CARD_WITH_WRONG_CARD_EXPIRATION_DATE = new CreditCardDTO.Builder()
            .withNumber("1234123412341234")
            .withCVV("911")
            .withPin("4444")
            .withFullName("MIKHAIL KHAREVICH")
            .withExpirationDate(LocalDate.parse("2020-12-11"))
            .build();

    @BeforeAll
    public void beforeAll() {
        this.validator = CreditCardValidator.getInstance();
    }

    @Test
    void validateForWrongCardNumber() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(CARD_WITH_WRONG_CARD_NUMBER));

        Assertions.assertEquals(CREDIT_CARD_NUMBER_EXCEPTION + DELIMITER + CREDIT_CARD_NUMBER_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongCardCVV() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(CARD_WITH_WRONG_CARD_CVV));

        Assertions.assertEquals(CVV_CODE_EXCEPTION + DELIMITER + CVV_CODE_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongCardPin() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(CARD_WITH_WRONG_CARD_PIN));

        Assertions.assertEquals(PIN_FORMAT_EXCEPTION + DELIMITER + PIN_FORMAT_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongCardFullName() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(CARD_WITH_WRONG_CARD_FULL_NAME));

        Assertions.assertEquals(FULL_NAME_EXCEPTION + DELIMITER + FULL_NAME_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongCardExpirationDate() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(CARD_WITH_WRONG_CARD_EXPIRATION_DATE));

        Assertions.assertEquals(EXPIRATION_DATE_EXCEPTION + DELIMITER + EXPIRATION_DATE_EXCEPTION_CODE,
                exception.getMessage());
    }
}