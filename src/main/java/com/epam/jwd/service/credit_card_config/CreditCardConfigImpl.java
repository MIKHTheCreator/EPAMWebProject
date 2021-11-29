package com.epam.jwd.service.credit_card_config;

public class CreditCardConfigImpl implements CreditCardConfig {

    private static final Integer CVV_LOWER_BOUND = 100;
    private static final Integer CVV_UPPER_BOUND = 900;
    private static final Integer PIN_LOWER_BOUND = 1000;
    private static final Integer PIN_UPPER_BOUND = 9000;
    private static final CreditCardConfig INSTANCE = new CreditCardConfigImpl();

    private CreditCardConfigImpl() {
    }

    public static CreditCardConfig getInstance() {
        return INSTANCE;
    }

    @Override
    public String generateCVVCode() {
        return String.valueOf((int) (CVV_LOWER_BOUND + (Math.random() * CVV_UPPER_BOUND)));
    }

    @Override
    public String generatePinCode() {
        return String.valueOf((int) (PIN_LOWER_BOUND + (Math.random() * PIN_UPPER_BOUND)));
    }
}
