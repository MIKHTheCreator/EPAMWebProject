package com.epam.jwd.service.parser.impl;

import com.epam.jwd.service.parser.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing Currency from xe-web-site
 *
 * @see Parser
 * Class created as Singleton
 */
public class CurrencyParser implements Parser {

    private static CurrencyParser instance = new CurrencyParser();
    private static final String CURRENCY_PATTERN = "<p class=\"result__BigRate-sc-1bsijpp-1 iGrAod\">(.+?)<span class=\"faded-digits\">";
    private static final String EMPTY_STRING = "";
    private static final String POINTER = ",";
    private static final Integer GROUP_ID = 1;

    private CurrencyParser() {
    }

    public static Parser getInstance() {
        if (instance == null) {
            instance = new CurrencyParser();
        }
        return instance;
    }

    /**
     * @see Parser#parse(String)
     */
    @Override
    public String parse(String stringToParse) {

        String result = EMPTY_STRING;
        Pattern pattern = Pattern.compile(CURRENCY_PATTERN);
        Matcher matcher = pattern.matcher(stringToParse);

        if (matcher.find()) {
            result = matcher.group(GROUP_ID).replace(POINTER, EMPTY_STRING);
        }

        return result;
    }
}
