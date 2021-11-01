package com.epam.jwd.service.password_manager.api;

public interface PasswordManager {

    String encode(String password);
    String decode(String password);
    boolean checkForIdentity(String firstString, String secondString);
}
