package com.epam.jwd.service.validator.user_account;

import com.epam.jwd.service.dto.user_account.PassportDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;

import java.time.LocalDate;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.config.ValidatorConfig.PERSONAL_NUMBER_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.SERIA_AND_NUMBER_PATTERN;
import static com.epam.jwd.service.message.ExceptionMessage.EXPIRATION_DATE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.EXPIRATION_DATE_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PERSONAL_NUMBER_MISSSMATCH;
import static com.epam.jwd.service.message.ExceptionMessage.PERSONAL_NUMBER_MISSMATCH_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERIA_AND_NUMBER_MISSMATCH;
import static com.epam.jwd.service.message.ExceptionMessage.SERIA_AND_NUMBER_MISSMATCH_CODE;

public class PassportValidator implements Validator<PassportDTO, Integer> {

    @Override
    public void validate(PassportDTO passportDTO) throws ServiceException {
        isValidSeriaAndNumber(passportDTO.getSeriaAndNumber());
        isValidPersonalNumber(passportDTO.getPersonalNumber());
        isValidExpirationDate(passportDTO.getExpirationDate());
    }

    private void isValidExpirationDate(LocalDate expirationDate) throws ServiceException {

         final LocalDate todayDate = LocalDate.now();
         if(!expirationDate.isAfter(todayDate)) {
             throw new ServiceException(EXPIRATION_DATE_EXCEPTION + DELIMITER + EXPIRATION_DATE_EXCEPTION_CODE);
         }
    }

    private void isValidPersonalNumber(String personalNumber) throws ServiceException {
        if(!personalNumber.matches(PERSONAL_NUMBER_PATTERN)) {
            throw new ServiceException(PERSONAL_NUMBER_MISSSMATCH + DELIMITER + PERSONAL_NUMBER_MISSMATCH_CODE);
        }
    }

    private void isValidSeriaAndNumber(String seriaAndNumber) throws ServiceException {
        if(!seriaAndNumber.matches(SERIA_AND_NUMBER_PATTERN)) {
            throw new ServiceException(SERIA_AND_NUMBER_MISSMATCH + DELIMITER + SERIA_AND_NUMBER_MISSMATCH_CODE);
        }
    }
}
