package com.epam.jwd.service.impl;

import com.epam.jwd.service.api.PasswordManager;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class PasswordManagerImpl implements PasswordManager {

    @Override
    public String encode(String password) {
        return Arrays.toString(Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String decode(String password) {
        return Arrays.toString(Base64.getDecoder().decode(password));
    }

    @Override
    public boolean checkForIdentity(String inputPassword, String encodedPassword) {
        return encode(inputPassword).equals(encodedPassword);
    }
}
