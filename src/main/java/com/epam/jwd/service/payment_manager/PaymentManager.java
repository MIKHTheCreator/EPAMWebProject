package com.epam.jwd.service.payment_manager;

import com.epam.jwd.service.dto.payment_system.PaymentGoal;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.payment_system.BankAccountCashValidator;

import java.math.BigDecimal;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION_CODE;

/**
 * Class for managing payments
 * Class created as Singleton
 *
 * @author mikh
 */
public class PaymentManager {

    private final BankAccountCashValidator validator = BankAccountCashValidator.getInstance();
    private static PaymentManager instance = new PaymentManager();
    private static final String SPACE_CHAR = "\s";
    private static final String UNDERSCORE = "_";
    private static final Integer SUBTRACT_OPERATION_NUMBER = 1;
    private static final Integer ADD_OPERATION_NUMBER = 0;

    private PaymentManager() {
    }

    public static PaymentManager getInstance() {
        if(instance == null) {
            instance = new PaymentManager();
        }

        return instance;
    }

    /**
     * Method for getting payment goal as a number
     *
     * @param goal string payment goal
     * @return id of goal operation
     */
    private int checkForPaymentGoal(String goal) {
        String convertedGoal = paymentGoalConverter(goal);
        return PaymentGoal.getOperationIdByName(convertedGoal);
    }

    /**
     * Method for converting input goal to the needed format
     *
     * @param inputGoal unconverted goal
     * @return converted goal as a String
     */
    private String paymentGoalConverter(String inputGoal) {
        return inputGoal.replace(SPACE_CHAR, UNDERSCORE).toUpperCase();
    }

    /**
     * Method for checking is it enough money to pay
     *
     * @param sumOfPayment sum of payment
     * @param currentCash  current bank account cash
     * @throws ServiceException if it's not enough money to pay
     */
    private void isEnoughMoney(BigDecimal sumOfPayment, BigDecimal currentCash) throws ServiceException {
        if (!validator.isEnoughCashForPayment(sumOfPayment, currentCash)) {
            throw new ServiceException(NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION + DELIMITER + NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION_CODE);
        }
    }

    /**
     * Method for choosing input operation
     *
     * @param goal         payment goal
     * @param sumOfPayment sum of payment
     * @param currentCash  current bank account cash
     * @return operation number(0-add operation, 1-subtract operation)
     * @throws ServiceException if it's not enough money to pay
     */
    public Integer chooseOperation(String goal, BigDecimal sumOfPayment, BigDecimal currentCash) throws ServiceException {
        if (checkForPaymentGoal(goal) == SUBTRACT_OPERATION_NUMBER) {
            isEnoughMoney(sumOfPayment, currentCash);
            return SUBTRACT_OPERATION_NUMBER;
        }

        return ADD_OPERATION_NUMBER;
    }
}
