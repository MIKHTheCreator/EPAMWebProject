package com.epam.jwd.service.parser;

/**
 * Parser interface which provides user with parse method to parse any structure
 *
 * @author mikh
 */
public interface Parser {

    /**
     * Method for parsing some string to elements
     *
     * @param stringToParse parsed string
     * @return parsed element
     */
    String parse(String stringToParse);
}
