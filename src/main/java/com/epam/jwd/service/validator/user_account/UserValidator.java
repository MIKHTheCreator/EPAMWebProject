package com.epam.jwd.service.validator.user_account;

import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.config.ValidatorConfig.MAX_AGE;
import static com.epam.jwd.service.config.ValidatorConfig.MAX_FIRST_NAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MAX_SECOND_NAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MIN_AGE;
import static com.epam.jwd.service.config.ValidatorConfig.MIN_FIRST_NAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.MIN_SECOND_NAME_LENGTH;
import static com.epam.jwd.service.config.ValidatorConfig.PHONE_NUMBER_PATTERN;
import static com.epam.jwd.service.message.ExceptionMessage.AGE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.AGE_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.FIRST_NAME_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.FIRST_NAME_LENGTH_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PHONE_NUMBER_MISS_MATCH;
import static com.epam.jwd.service.message.ExceptionMessage.PHONE_NUMBER_MISS_MATCH_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SECOND_NAME_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SECOND_NAME_LENGTH_EXCEPTION_CODE;

public class UserValidator implements Validator<UserDTO, Integer> {

    private static final Validator<UserDTO, Integer> INSTANCE = new UserValidator();

    private UserValidator() {
    }

    public static Validator<UserDTO, Integer> getInstance() {
        return INSTANCE;
    }

    @Override
    public void validate(UserDTO userDTO) throws ServiceException {
        isValidFirstName(userDTO.getFirstName());
        isValidSecondName(userDTO.getSecondName());
        isValidPhoneNumber(userDTO.getPhoneNumber());
        isValidAge(userDTO.getAge());
    }

    private void isValidFirstName(String firstName) throws ServiceException {
        if (!isValidFirstNameLength(firstName)) {
            throw new ServiceException(FIRST_NAME_LENGTH_EXCEPTION + DELIMITER + FIRST_NAME_LENGTH_EXCEPTION_CODE);
        }
    }

    private boolean isValidFirstNameLength(String firstName) {
        final int firstNameLength = firstName.length();
        return firstNameLength >= MIN_FIRST_NAME_LENGTH && firstNameLength <= MAX_FIRST_NAME_LENGTH;
    }

    private void isValidSecondName(String secondName) throws ServiceException {
        if (!isValidSecondNameLength(secondName)) {
            throw new ServiceException(SECOND_NAME_LENGTH_EXCEPTION + DELIMITER + SECOND_NAME_LENGTH_EXCEPTION_CODE);
        }
    }

    private boolean isValidSecondNameLength(String secondName) {
        final int secondNameLength = secondName.length();
        return secondNameLength >= MIN_SECOND_NAME_LENGTH && secondNameLength <= MAX_SECOND_NAME_LENGTH;
    }

    private void isValidPhoneNumber(String phoneNumber) throws ServiceException {
        if (!phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            throw new ServiceException(PHONE_NUMBER_MISS_MATCH + DELIMITER + PHONE_NUMBER_MISS_MATCH_CODE);
        }
    }

    private void isValidAge(Integer age) throws ServiceException {
        if (!isValidAgeRange(age)) {
            throw new ServiceException(AGE_EXCEPTION + DELIMITER + AGE_EXCEPTION_CODE);
        }
    }

    private boolean isValidAgeRange(Integer age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }
}
