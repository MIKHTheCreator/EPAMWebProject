package com.epam.jwd.service.payment_manager;

import com.epam.jwd.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentManagerTest {

    private PaymentManager manager;
    private static final String PAYMENT_GOAL_CHARITY = "Charity";
    private static final String PAYMENT_GOAL_BANK_ACCOUNT_REPLENISHMENT = "Bank Account Replenishment";
    private static final BigDecimal CURRENT_CASH_ENOUGH = new BigDecimal("150");
    private static final BigDecimal CURRENT_CASH_NOT_ENOUGH = new BigDecimal("100");
    private static final BigDecimal SUM_TO_PAY = new BigDecimal("110");
    private static final Integer SUBTRACT_OPERATION_NUMBER = 1;
    private static final Integer ADD_OPERATION_NUMBER = 0;

    @BeforeAll
    public void beforeAll() {
        this.manager = PaymentManager.getInstance();
    }

    @Test
    void chooseAddOperation() throws ServiceException {
        assertEquals(ADD_OPERATION_NUMBER, manager.chooseOperation(PAYMENT_GOAL_BANK_ACCOUNT_REPLENISHMENT, SUM_TO_PAY, CURRENT_CASH_ENOUGH));
    }

    @Test
    void chooseSubtractOperation() throws ServiceException {
        assertEquals(SUBTRACT_OPERATION_NUMBER, manager.chooseOperation(PAYMENT_GOAL_CHARITY, SUM_TO_PAY, CURRENT_CASH_ENOUGH));
    }

    @Test
    void chooseSubtractOperationFail() {
        ServiceException exception = assertThrows(ServiceException.class, () -> manager.chooseOperation(PAYMENT_GOAL_CHARITY, SUM_TO_PAY, CURRENT_CASH_NOT_ENOUGH));
        assertEquals(NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION + DELIMITER + NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION_CODE, exception.getMessage());
    }
}