package com.epam.jwd.service.credit_card_config;

/**
 * Interface which describes method for generating Credit Card CVV code and Pin code
 *
 * @author mikh
 */
public interface CreditCardConfig {

    /**
     * Method for generating 3 symbol long CVV code
     *
     * @return String 3-number cvv code
     */
    String generateCVVCode();

    /**
     * Method for generating 4 symbol long Credit Card Pin code
     *
     * @return String 4-number Pin code
     */
    String generatePinCode();
}
