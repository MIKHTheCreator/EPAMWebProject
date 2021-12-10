package com.epam.jwd.service.currency_converter;


import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.parser.Parser;
import com.epam.jwd.service.parser.impl.CurrencyParser;

import java.math.BigDecimal;

public class CurrencyConverter {

    private final Parser parser = CurrencyParser.getInstance();
    private final RequestSender sender = RequestSender.getInstance();
    private static final CurrencyConverter INSTANCE = new CurrencyConverter();

    private CurrencyConverter() {
    }

    public static CurrencyConverter getInstance() {
        return INSTANCE;
    }

    public BigDecimal convert(String amount, String from, String to)
            throws ServiceException {
        String beforeConvert = sender.prepareSendRequest(amount, from, to);

        return convertStringToNumber(beforeConvert);
    }

    private BigDecimal convertStringToNumber(String number) {

        String result = parser.parse(number);
        return new BigDecimal(result);
    }
}
