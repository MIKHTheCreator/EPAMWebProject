package com.epam.jwd.service.currency_converter;

import com.epam.jwd.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurrencyConverterTest {

    private CurrencyConverter converter;

    @BeforeAll
    public void beforeAll() {
        this.converter = CurrencyConverter.getInstance();
    }

    @Test
    void convert() throws ServiceException {
        System.out.println(converter.convert("555500", "USD", "EUR"));
    }
}