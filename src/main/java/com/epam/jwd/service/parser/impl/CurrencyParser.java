package com.epam.jwd.service.parser.impl;

import com.epam.jwd.service.parser.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyParser implements Parser {

    private static final CurrencyParser INSTANCE = new CurrencyParser();
    private static final String CURRENCY_PATTERN = "<p class=\"result__BigRate-sc-1bsijpp-1 iGrAod\">(.+?)<span class=\"faded-digits\">";
    private static final String EMPTY_STRING = "";
    private static final String POINTER = ",";

    private CurrencyParser() {
    }

    public static Parser getInstance() {
        return INSTANCE;
    }

    @Override
    public String parse(String stringToParse) {

        String result = EMPTY_STRING;
        Pattern pattern = Pattern.compile(CURRENCY_PATTERN);
        Matcher matcher = pattern.matcher(stringToParse);

        if (matcher.find()) {
            result = matcher.group(1).replace(POINTER, EMPTY_STRING);
        }

        return result;
    }
}
