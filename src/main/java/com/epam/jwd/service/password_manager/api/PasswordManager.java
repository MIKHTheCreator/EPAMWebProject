package com.epam.jwd.service.password_manager.api;

/**
 * Interface that provides methods to encode and decode strings
 *
 * @author mikh
 */
public interface PasswordManager {

    /**
     * Method which encode string using Base64 algorithms
     *
     * @param password string to encode
     * @return encoded string
     */
    String encode(String password);

    /**
     * Method for decoding inputed string
     *
     * @param password string to decode
     * @return decoded string
     */
    String decode(String password);

    /**
     * Method for checking to strings for identity
     *
     * @param firstString  decoded string
     * @param secondString encoded string
     * @return true if strings are equals false otherwise
     */
    boolean checkForIdentity(String firstString, String secondString);
}
