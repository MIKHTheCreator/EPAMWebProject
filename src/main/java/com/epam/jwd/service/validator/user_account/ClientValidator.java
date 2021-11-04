package com.epam.jwd.service.validator.user_account;

import com.epam.jwd.service.dto.user_account.ClientDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.config.ValidatorConfig.MAX_USERNAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MIN_USERNAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.USERNAME_SPECIAL_SYMBOLS;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_CONTAINS_SPECIAL_SYMBOLS_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_CONTAINS_SPECIAL_SYMBOLS_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.USERNAME_LENGTH_EXCEPTION_CODE;

public class ClientValidator implements Validator<ClientDTO, Integer> {

    @Override
    public void validate(ClientDTO client) throws ServiceException {
        isValidUsername(client.getUsername());
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
}
