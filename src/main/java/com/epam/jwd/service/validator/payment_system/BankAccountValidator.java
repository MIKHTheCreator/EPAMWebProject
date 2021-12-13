package com.epam.jwd.service.validator.payment_system;

import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;

import java.math.BigDecimal;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.config.ValidatorConfig.CURRENCY_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.POSITIVE_NUMBER;
import static com.epam.jwd.service.message.ExceptionMessage.CURRENCY_MISS_MATCH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.CURRENCY_MISS_MATCH_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.INVALID_BALANCE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.INVALID_BALANCE_EXCEPTION_CODE;

public class BankAccountValidator implements Validator<BankAccountDTO, Integer> {

    private static Validator<BankAccountDTO, Integer> instance = new BankAccountValidator();

    private BankAccountValidator() {
    }

    public static Validator<BankAccountDTO, Integer> getInstance() {
        if (instance == null) {
            instance = new BankAccountValidator();
        }
        return instance;
    }

    @Override
    public void validate(BankAccountDTO bankAccountDTO) throws ServiceException {
        isValidBalance(bankAccountDTO.getBalance());
        isValidCurrency(bankAccountDTO.getCurrency());
    }

    private void isValidCurrency(String currency) throws ServiceException {
        if (!currency.toUpperCase().matches(CURRENCY_PATTERN)) {
            throw new ServiceException(CURRENCY_MISS_MATCH_EXCEPTION + DELIMITER + CURRENCY_MISS_MATCH_EXCEPTION_CODE);
        }
    }

    private void isValidBalance(BigDecimal balance) throws ServiceException {
        if (!isPositiveNumber(balance)) {
            throw new ServiceException(INVALID_BALANCE_EXCEPTION + DELIMITER + INVALID_BALANCE_EXCEPTION_CODE);
        }
    }

    private boolean isPositiveNumber(BigDecimal number) {
        return number.doubleValue() >= POSITIVE_NUMBER;
    }
}
