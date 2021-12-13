package com.epam.jwd.service.password_manager;

import com.epam.jwd.service.password_manager.api.PasswordManager;
import org.apache.xerces.impl.dv.util.Base64;

import java.nio.charset.StandardCharsets;

/**
 * PasswordManager Class which implements PasswordManager and provides methods for work with Base64 encoder
 * @author mikh
 * @see PasswordManager
 */
public class PasswordManagerImpl implements PasswordManager {

    /**
     * @see PasswordManager#encode(String)
     */
    @Override
    public String encode(String password) {
        return Base64.encode(password.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @see PasswordManager#decode(String)
     */
    @Override
    public String decode(String password) {
        StringBuilder result = new StringBuilder();
        for (int i : Base64.decode(password)) {
            result.append(Character.toString(i));
        }
        return result.toString();
    }

    /**
     * @see PasswordManager#checkForIdentity(String, String)
     */
    @Override
    public boolean checkForIdentity(String inputPassword, String encodedPassword) {
        return inputPassword.equals(encodedPassword);
    }
}
