package com.epam.jwd.service.validator.user_account;

import com.epam.jwd.service.dto.user_account.ClientDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;

import java.util.Locale;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.config.ValidatorConfig.EMAIL_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.MAX_PASSWORD_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MAX_USERNAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MIN_PASSWORD_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MIN_USERNAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.USERNAME_SPECIAL_SYMBOLS;
import static com.epam.jwd.service.message.ExceptionMessage.PASSWORD_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PASSWORD_LENGTH_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.UNSUPPORTED_EMAIL;
import static com.epam.jwd.service.message.ExceptionMessage.UNSUPPORTED_EMAIL_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_CONTAINS_SPECIAL_SYMBOLS_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_CONTAINS_SPECIAL_SYMBOLS_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_LENGTH_EXCEPTION_CODE;

public class ClientValidator implements Validator<ClientDTO, Integer> {

    @Override
    public void validate(ClientDTO client) throws ServiceException {
        isValidUsername(client.getUsername());
        isValidEmail(client.getEmail());
        isValidPassword(client.getPassword());
    }

    private void isValidPassword(String password) throws ServiceException {
        if (!isValidPasswordLength(password)) {
            throw new ServiceException(PASSWORD_LENGTH_EXCEPTION + DELIMITER + PASSWORD_LENGTH_EXCEPTION_CODE);
        } else if (!isContainUpperCaseLetters(password)) {
            throw new ServiceException(PASSWORD_LENGTH_EXCEPTION + DELIMITER + PASSWORD_LENGTH_EXCEPTION_CODE);
        }
    }

    //todo check method for upper and lower case letters in text
    private boolean isContainUpperCaseLetters(String password) {
        return
    }

    private boolean isValidPasswordLength(String password) {

        final int passwordLength = password.length();
        return passwordLength >= MIN_PASSWORD_LENGTH && passwordLength <= MAX_PASSWORD_LENGTH;
    }

    private void isValidUsername(String username) throws ServiceException {

        if (!isValidUsernameLength(username)) {
            throw new ServiceException(USERNAME_LENGTH_EXCEPTION + DELIMITER + USERNAME_LENGTH_EXCEPTION_CODE);
        } else if (isContainSpecialSymbols(username)) {
            throw new ServiceException(USERNAME_CONTAINS_SPECIAL_SYMBOLS_EXCEPTION + DELIMITER + USERNAME_CONTAINS_SPECIAL_SYMBOLS_EXCEPTION_CODE);
        }
    }

    private boolean isContainSpecialSymbols(String username) {
        return username.matches(USERNAME_SPECIAL_SYMBOLS);
    }

    private boolean isValidUsernameLength(String username) {

        final int usernameLength = username.length();
        return usernameLength >= MIN_USERNAME_LENGTH && usernameLength <= MAX_USERNAME_LENGTH;
    }

    private void isValidEmail(String email) throws ServiceException {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new ServiceException(UNSUPPORTED_EMAIL + DELIMITER + UNSUPPORTED_EMAIL_CODE);
        }
    }
}
