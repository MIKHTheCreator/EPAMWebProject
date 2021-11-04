package com.epam.jwd.service.validator.user_account;

import com.epam.jwd.service.dto.user_account.ClientDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.config.ValidatorConfig.EMAIL_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.MAX_PASSWORD_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MAX_USERNAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MIN_PASSWORD_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MIN_USERNAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.PASSWORD_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.USERNAME_PATTERN;
import static com.epam.jwd.service.message.ExceptionMessage.PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PASSWORD_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PASSWORD_LENGTH_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.UNSUPPORTED_EMAIL;
import static com.epam.jwd.service.message.ExceptionMessage.UNSUPPORTED_EMAIL_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_LENGTH_EXCEPTION_CODE;

public class ClientValidator implements Validator<ClientDTO, Integer> {

    @Override
    public void validate(ClientDTO clientDTO) throws ServiceException {
        isValidUsername(clientDTO.getUsername());
        isValidEmail(clientDTO.getEmail());
        isValidPassword(clientDTO.getPassword());
    }

    private void isValidUsername(String username) throws ServiceException {

        if (!isValidUsernameLength(username)) {
            throw new ServiceException(USERNAME_LENGTH_EXCEPTION + DELIMITER + USERNAME_LENGTH_EXCEPTION_CODE);
        } else if (!isCorrespondToUsernamePattern(username)) {
            throw new ServiceException(USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION + DELIMITER + USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION_CODE);
        }
    }

    private void isValidEmail(String email) throws ServiceException {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new ServiceException(UNSUPPORTED_EMAIL + DELIMITER + UNSUPPORTED_EMAIL_CODE);
        }
    }

    private void isValidPassword(String password) throws ServiceException {
        if (!isValidPasswordLength(password)) {
            throw new ServiceException(PASSWORD_LENGTH_EXCEPTION + DELIMITER + PASSWORD_LENGTH_EXCEPTION_CODE);
        } else if (!isCorrespondToPasswordPattern(password)) {
            throw new ServiceException(PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION + DELIMITER + PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION_CODE);
        }
    }

    private boolean isCorrespondToPasswordPattern(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    private boolean isValidPasswordLength(String password) {

        final int passwordLength = password.length();
        return passwordLength >= MIN_PASSWORD_LENGTH && passwordLength <= MAX_PASSWORD_LENGTH;
    }

    private boolean isCorrespondToUsernamePattern(String username) {
        return username.matches(USERNAME_PATTERN);
    }

    private boolean isValidUsernameLength(String username) {

        final int usernameLength = username.length();
        return usernameLength >= MIN_USERNAME_LENGTH && usernameLength <= MAX_USERNAME_LENGTH;
    }
}
