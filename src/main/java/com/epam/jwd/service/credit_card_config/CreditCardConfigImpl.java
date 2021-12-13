package com.epam.jwd.service.credit_card_config;

/**
 * CreditCardConfig class implementation of CreditCardConfig interface with overriden methods
 * Class created by Singleton pattern
 *
 * @see CreditCardConfig
 */
public class CreditCardConfigImpl implements CreditCardConfig {

    /**
     * Lower bound for CVV code
     */
    private static final Integer CVV_LOWER_BOUND = 100;
    /**
     * Upper bound for CVV code
     */
    private static final Integer CVV_UPPER_BOUND = 900;
    /**
     * Lower bound for Pin code
     */
    private static final Integer PIN_LOWER_BOUND = 1000;
    /**
     * Upper bound for Pin code
     */
    private static final Integer PIN_UPPER_BOUND = 9000;
    private static final CreditCardConfig INSTANCE = new CreditCardConfigImpl();

    private CreditCardConfigImpl() {
    }

    public static CreditCardConfig getInstance() {
        return INSTANCE;
    }

    /**
     * Method which generates code by random
     *
     * @return Random CVV code
     * @see CreditCardConfig#generateCVVCode()
     */
    @Override
    public String generateCVVCode() {
        return String.valueOf((int) (CVV_LOWER_BOUND + (Math.random() * CVV_UPPER_BOUND)));
    }

    /**
     * Method which generates code by random
     *
     * @return Random Pin code
     * @see CreditCardConfig#generatePinCode()
     */
    @Override
    public String generatePinCode() {
        return String.valueOf((int) (PIN_LOWER_BOUND + (Math.random() * PIN_UPPER_BOUND)));
    }
}
