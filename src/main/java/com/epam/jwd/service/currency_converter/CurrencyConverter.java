package com.epam.jwd.service.currency_converter;


import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.parser.Parser;
import com.epam.jwd.service.parser.impl.CurrencyParser;

import java.math.BigDecimal;

/**
 * Currency Converter class which provides user with converting currency into CurrencyEnum Field
 * Class created by Singleton Pattern
 */
public class CurrencyConverter {

    /**
     * @see Parser
     */
    private final Parser parser = CurrencyParser.getInstance();
    /**
     * @see RequestSender
     */
    private final RequestSender sender = RequestSender.getInstance();
    private static CurrencyConverter instance = new CurrencyConverter();

    private CurrencyConverter() {
    }

    public static CurrencyConverter getInstance() {
        if (instance == null) {
            instance = new CurrencyConverter();
        }
        return instance;
    }

    /**
     * Method for converting input currency amount to another currency
     *
     * @param amount input balance
     * @param from   currency from to convert
     * @param to     currency to convert to
     * @return BigDecimal balance in different currency
     * @throws ServiceException if any service exception was thrown
     */
    public BigDecimal convert(String amount, String from, String to)
            throws ServiceException {
        String beforeConvert = sender.prepareSendRequest(amount, from, to);

        return convertStringToNumber(beforeConvert);
    }

    /**
     * Method for converting String number field to BigDecimal number field
     *
     * @param number String balance
     * @return BigDecimal balance
     */
    private BigDecimal convertStringToNumber(String number) {

        String result = parser.parse(number);
        return new BigDecimal(result);
    }
}
