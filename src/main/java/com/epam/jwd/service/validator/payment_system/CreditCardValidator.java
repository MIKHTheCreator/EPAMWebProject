package com.epam.jwd.service.validator.payment_system;

import com.epam.jwd.service.dto.payment_system.CreditCardDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;

import java.time.LocalDate;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.config.ValidatorConfig.CREDIT_CARD_NUMBER_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.CVV_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.FULL_NAME_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.PIN_PATTERN;
import static com.epam.jwd.service.message.ExceptionMessage.CREDIT_CARD_NUMBER_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.CREDIT_CARD_NUMBER_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.CVV_CODE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.CVV_CODE_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.EXPIRATION_DATE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.EXPIRATION_DATE_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.FULL_NAME_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.FULL_NAME_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PIN_FORMAT_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PIN_FORMAT_EXCEPTION_CODE;

public class CreditCardValidator implements Validator<CreditCardDTO, Integer> {

    @Override
    public void validate(CreditCardDTO creditCardDTO) throws ServiceException {
        isValidCreditCardNumber(creditCardDTO.getNumber());
        isValidExpirationDate(creditCardDTO.getExpirationDate());
        isValidFullName(creditCardDTO.getFullName());
        isValidCVV(creditCardDTO.getCVV());
        isValidPin(creditCardDTO.getPin());
    }

    private void isValidPin(String pin) throws ServiceException {
        if (!pin.matches(PIN_PATTERN)) {
            throw new ServiceException(PIN_FORMAT_EXCEPTION + DELIMITER + PIN_FORMAT_EXCEPTION_CODE);
        }
    }

    private void isValidCVV(String cvv) throws ServiceException {
        if (!cvv.matches(CVV_PATTERN)) {
            throw new ServiceException(CVV_CODE_EXCEPTION + DELIMITER + CVV_CODE_EXCEPTION_CODE);
        }
    }

    private void isValidFullName(String fullName) throws ServiceException {
        if (!fullName.matches(FULL_NAME_PATTERN)) {
            throw new ServiceException(FULL_NAME_EXCEPTION + DELIMITER + FULL_NAME_EXCEPTION_CODE);
        }
    }

    private void isValidExpirationDate(LocalDate expirationDate) throws ServiceException {
        final LocalDate todayDate = LocalDate.now();
        if (!expirationDate.isAfter(todayDate)) {
            throw new ServiceException(EXPIRATION_DATE_EXCEPTION + DELIMITER + EXPIRATION_DATE_EXCEPTION_CODE);
        }
    }

    private void isValidCreditCardNumber(String number) throws ServiceException {
        if (!number.matches(CREDIT_CARD_NUMBER_PATTERN)) {
            throw new ServiceException(CREDIT_CARD_NUMBER_EXCEPTION + DELIMITER + CREDIT_CARD_NUMBER_EXCEPTION_CODE);
        }
    }
}
