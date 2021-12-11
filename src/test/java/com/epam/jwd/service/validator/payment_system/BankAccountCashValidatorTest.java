package com.epam.jwd.service.validator.payment_system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountCashValidatorTest {

    private BankAccountCashValidator validator;
    private static final BigDecimal enoughCash = new BigDecimal("100");
    private static final BigDecimal notEnoughCash = new BigDecimal("0");
    private static final BigDecimal paymentSum = new BigDecimal("90");
    private static final BigDecimal paymentSumMax = new BigDecimal("100");

    @BeforeAll
    public void beforeAll() {
        this.validator = BankAccountCashValidator.getInstance();
    }

    @Test
    void isEnoughCashForPayment() {
        assertTrue(validator.isEnoughCashForPayment(paymentSum, enoughCash));
    }

    @Test
    void isNotEnoughCashForPayment() {
        assertFalse(validator.isEnoughCashForPayment(paymentSum, notEnoughCash));
    }

    @Test
    void isEnoughCashForPaymentAtZero() {
        assertTrue(validator.isEnoughCashForPayment(paymentSumMax, enoughCash));
    }
}