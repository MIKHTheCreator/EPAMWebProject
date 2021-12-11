package com.epam.jwd.service.validator.payment_system;

import com.epam.jwd.service.dto.payment_system.PaymentDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.DATE_OF_PAYMENT_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.DATE_OF_PAYMENT_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PAYMENT_GOAL_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PAYMENT_GOAL_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PAYMENT_ORGANIZATION_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PAYMENT_ORGANIZATION_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.POSITIVE_PAYMENT_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.POSITIVE_PAYMENT_EXCEPTION_CODE;
import static org.junit.Assert.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentValidatorTest {

    private Validator<PaymentDTO, Integer> validator;
    private static final PaymentDTO PAYMENT_WITH_WRONG_SUM = new PaymentDTO.Builder()
            .withSumOfPayment(new BigDecimal("-30"))
            .withPaymentGoal("Charity")
            .withPaymentOrganization("Belinvest Bank")
            .withDateOfPayment(LocalDate.parse("2021-12-11"))
            .build();
    private static final PaymentDTO PAYMENT_WITH_WRONG_PAYMENT_GOAL = new PaymentDTO.Builder()
            .withSumOfPayment(new BigDecimal("30"))
            .withPaymentGoal("")
            .withPaymentOrganization("Belinvest Bank")
            .withDateOfPayment(LocalDate.parse("2021-12-11"))
            .build();
    private static final PaymentDTO PAYMENT_WITH_WRONG_PAYMENT_ORGANIZATION = new PaymentDTO.Builder()
            .withSumOfPayment(new BigDecimal("30"))
            .withPaymentGoal("Charity")
            .withPaymentOrganization("Belinvest*Bank")
            .withDateOfPayment(LocalDate.parse("2021-12-11"))
            .build();
    private static final PaymentDTO PAYMENT_WITH_WRONG_DATE_OF_PAYMENT = new PaymentDTO.Builder()
            .withSumOfPayment(new BigDecimal("30"))
            .withPaymentGoal("Charity")
            .withPaymentOrganization("Belinvest Bank")
            .withDateOfPayment(LocalDate.parse("2021-12-14"))
            .build();

    @BeforeAll
    public void beforeAll() {
        this.validator = PaymentValidator.getInstance();
    }

    @Test
    void validateForWrongPaymentSum() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(PAYMENT_WITH_WRONG_SUM));

        Assertions.assertEquals(POSITIVE_PAYMENT_EXCEPTION + DELIMITER + POSITIVE_PAYMENT_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongPaymentGoal() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(PAYMENT_WITH_WRONG_PAYMENT_GOAL));

        Assertions.assertEquals(PAYMENT_GOAL_EXCEPTION + DELIMITER + PAYMENT_GOAL_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongPaymentOrganization() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(PAYMENT_WITH_WRONG_PAYMENT_ORGANIZATION));

        Assertions.assertEquals(PAYMENT_ORGANIZATION_EXCEPTION + DELIMITER + PAYMENT_ORGANIZATION_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongPaymentDate() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(PAYMENT_WITH_WRONG_DATE_OF_PAYMENT));

        Assertions.assertEquals(DATE_OF_PAYMENT_EXCEPTION + DELIMITER + DATE_OF_PAYMENT_EXCEPTION_CODE,
                exception.getMessage());
    }
}