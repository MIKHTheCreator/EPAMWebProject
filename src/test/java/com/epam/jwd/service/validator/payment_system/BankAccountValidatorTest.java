package com.epam.jwd.service.validator.payment_system;

import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.CURRENCY_MISS_MATCH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.CURRENCY_MISS_MATCH_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.INVALID_BALANCE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.INVALID_BALANCE_EXCEPTION_CODE;
import static org.junit.Assert.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountValidatorTest {

    private Validator<BankAccountDTO, Integer> validator;
    private static final BankAccountDTO ACCOUNT_WITH_WRONG_CURRENCY = new BankAccountDTO(new BigDecimal("30"), "CD", false);
    private static final BankAccountDTO ACCOUNT_WITH_WRONG_STARTER_BALANCE = new BankAccountDTO(new BigDecimal("-30"), "USD", false);

    @BeforeAll
    public void beforeAll() {
        this.validator = BankAccountValidator.getInstance();
    }

    @Test
    void validateForWrongAccountCurrency() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(ACCOUNT_WITH_WRONG_CURRENCY));

        Assertions.assertEquals(CURRENCY_MISS_MATCH_EXCEPTION + DELIMITER + CURRENCY_MISS_MATCH_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongAccountStarterBalance() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(ACCOUNT_WITH_WRONG_STARTER_BALANCE));

        Assertions.assertEquals(INVALID_BALANCE_EXCEPTION + DELIMITER + INVALID_BALANCE_EXCEPTION_CODE,
                exception.getMessage());
    }
}