package com.epam.jwd.service.credit_card_config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreditCardConfigImplTest {

    private CreditCardConfig creditCardConfig;
    private static final Integer CVV_CODE_LENGTH = 3;
    private static final Integer PIN_CODE_LENGTH = 4;
    private static final Integer NUM_OF_TESTS = 100;

    @BeforeAll
    public void beforeAll() {
        creditCardConfig = CreditCardConfigImpl.getInstance();
    }

    @Test
    void generateCVVCode() {
        for (int i =0; i < NUM_OF_TESTS; i++) {
            assertEquals((int) CVV_CODE_LENGTH, creditCardConfig.generateCVVCode().length());
        }
    }

    @Test
    void generatePinCode() {
        for (int i =0; i < NUM_OF_TESTS; i++) {
            assertEquals((int) PIN_CODE_LENGTH, creditCardConfig.generatePinCode().length());
        }
    }
}