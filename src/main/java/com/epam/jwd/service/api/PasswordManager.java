package com.epam.jwd.service.api;

public interface PasswordManager {

    String encode(String password);
    String decode(String password);
    boolean checkForIdentity(String firstString, String secondString);
}
