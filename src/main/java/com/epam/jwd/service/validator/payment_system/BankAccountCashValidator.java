package com.epam.jwd.service.validator.payment_system;


import java.math.BigDecimal;

public class BankAccountCashValidator {

    private static final BankAccountCashValidator INSTANCE = new BankAccountCashValidator();

    private BankAccountCashValidator() {
    }

    public static BankAccountCashValidator getInstance() {

        return INSTANCE;
    }

    public boolean isEnoughCashForPayment(BigDecimal sumOfPayment, BigDecimal currentCash) {
        return currentCash.subtract(sumOfPayment).compareTo(BigDecimal.ZERO) >= 0;
    }
}
