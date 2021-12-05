package com.epam.jwd.service.currency_converter;

import java.math.BigDecimal;

public class CurrencyConverter {

    private static final CurrencyConverter INSTANCE = new CurrencyConverter();

    private CurrencyConverter() {
    }

    public static CurrencyConverter getInstance() {
        return INSTANCE;
    }

}
