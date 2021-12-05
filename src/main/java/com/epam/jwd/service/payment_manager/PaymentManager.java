package com.epam.jwd.service.payment_manager;

import com.epam.jwd.service.dto.payment_system.PaymentGoal;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.payment_system.BankAccountCashValidator;

import java.math.BigDecimal;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION_CODE;

public class PaymentManager {

    private final BankAccountCashValidator validator = BankAccountCashValidator.getInstance();
    private static final PaymentManager INSTANCE = new PaymentManager();
    private static final String SPACE_CHAR = "\s";
    private static final String UNDERSCORE = "_";
    private static final Integer SUBTRACT_OPERATION_NUMBER = 1;
    private static final Integer ADD_OPERATION_NUMBER = 0;

    private PaymentManager() {
    }

    public static PaymentManager getInstance() {
        return INSTANCE;
    }

    private int checkForPaymentGoal(String goal) {
        String convertedGoal = paymentGoalConverter(goal);
        return PaymentGoal.getOperationIdByName(convertedGoal);
    }

    private String paymentGoalConverter(String inputGoal) {
        return inputGoal.replace(SPACE_CHAR, UNDERSCORE).toUpperCase();
    }

    private void isEnoughMoney(BigDecimal sumOfPayment, BigDecimal currentCash) throws ServiceException {
        if (!validator.isEnoughCashForPayment(sumOfPayment, currentCash)) {
            throw new ServiceException(NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION + DELIMITER + NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION_CODE);
        }
    }

    public Integer chooseOperation(String goal, BigDecimal sumOfPayment, BigDecimal currentCash) throws ServiceException {
        if (checkForPaymentGoal(goal) == SUBTRACT_OPERATION_NUMBER) {
            isEnoughMoney(sumOfPayment, currentCash);
            return SUBTRACT_OPERATION_NUMBER;
        }

        return ADD_OPERATION_NUMBER;
    }
}
