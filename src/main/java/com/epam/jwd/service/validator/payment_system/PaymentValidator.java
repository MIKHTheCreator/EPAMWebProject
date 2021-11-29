package com.epam.jwd.service.validator.payment_system;

import com.epam.jwd.service.dto.payment_system.PaymentDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.config.ValidatorConfig.PAYMENT_GOAL_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.PAYMENT_ORGANIZATION_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.POSITIVE_NUMBER;
import static com.epam.jwd.service.message.ExceptionMessage.DATE_OF_PAYMENT_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.DATE_OF_PAYMENT_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PAYMENT_GOAL_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PAYMENT_GOAL_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PAYMENT_ORGANIZATION_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.PAYMENT_ORGANIZATION_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.POSITIVE_PAYMENT_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.POSITIVE_PAYMENT_EXCEPTION_CODE;

public class PaymentValidator implements Validator<PaymentDTO, Integer> {

    private static final Validator<PaymentDTO, Integer> INSTANCE = new PaymentValidator();

    private PaymentValidator() {
    }

    public static Validator<PaymentDTO, Integer> getInstance() {
        return INSTANCE;
    }

    @Override
    public void validate(PaymentDTO paymentDTO) throws ServiceException {
        isValidSum(paymentDTO.getSumOfPayment());
        isValidDate(paymentDTO.getDateOfPayment());
        isValidOrganization(paymentDTO.getPaymentOrganization());
        isValidGoal(paymentDTO.getPaymentGoal());
    }

    private void isValidSum(BigDecimal sumOfPayment) throws ServiceException {
        if (!(sumOfPayment.doubleValue() >= POSITIVE_NUMBER)) {
            throw new ServiceException(POSITIVE_PAYMENT_EXCEPTION + DELIMITER + POSITIVE_PAYMENT_EXCEPTION_CODE);
        }
    }

    private void isValidDate(LocalDate dateOfPayment) throws ServiceException {

        final LocalDate todayDate = LocalDate.now();
        if (!(dateOfPayment.isEqual(todayDate) || dateOfPayment.isBefore(todayDate))) {
            throw new ServiceException(DATE_OF_PAYMENT_EXCEPTION + DELIMITER + DATE_OF_PAYMENT_EXCEPTION_CODE);
        }
    }

    private void isValidOrganization(String paymentOrganization) throws ServiceException {
        if (!paymentOrganization.matches(PAYMENT_ORGANIZATION_PATTERN)) {
            throw new ServiceException(PAYMENT_ORGANIZATION_EXCEPTION + DELIMITER + PAYMENT_ORGANIZATION_EXCEPTION_CODE);
        }
    }

    private void isValidGoal(String paymentGoal) throws ServiceException {
        if (!paymentGoal.matches(PAYMENT_GOAL_PATTERN)) {
            throw new ServiceException(PAYMENT_GOAL_EXCEPTION + DELIMITER + PAYMENT_GOAL_EXCEPTION_CODE);
        }
    }
}
