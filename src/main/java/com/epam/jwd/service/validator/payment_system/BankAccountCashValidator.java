package com.epam.jwd.service.validator.payment_system;


import java.math.BigDecimal;

public class BankAccountCashValidator {

    private static BankAccountCashValidator instance = new BankAccountCashValidator();

    private BankAccountCashValidator() {
    }

    public static BankAccountCashValidator getInstance() {
        if (instance == null) {
            instance = new BankAccountCashValidator();
        }
        return instance;
    }

    public boolean isEnoughCashForPayment(BigDecimal sumOfPayment, BigDecimal currentCash) {
        return currentCash.subtract(sumOfPayment).compareTo(BigDecimal.ZERO) >= 0;
    }
}
