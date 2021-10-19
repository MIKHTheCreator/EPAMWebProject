package com.epam.jwd.service.impl;

import com.epam.jwd.service.api.PasswordManager;
import org.apache.xerces.impl.dv.util.Base64;

import java.nio.charset.StandardCharsets;


public class PasswordManagerImpl implements PasswordManager {

    @Override
    public String encode(String password) {
        return Base64.encode(password.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decode(String password) {
        StringBuilder result = new StringBuilder();
        for(int i : Base64.decode(password)) {
            result.append(Character.toString(i));
        }
        return result.toString();
    }

    @Override
    public boolean checkForIdentity(String inputPassword, String encodedPassword) {
        return encode(inputPassword).equals(encodedPassword);
    }
}
