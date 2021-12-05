package com.epam.jwd.service.dto.payment_system;

import java.util.Arrays;

public enum PaymentGoal {
    BANK_ACCOUNT_REPLENISHMENT(0),
    TAX_PAYMENT(1),
    CHARITY(1),
    BANK_DEPOSIT(1),
    DEFAULT(0);

    private final int operation;

    PaymentGoal(int operation) {
        this.operation = operation;
    }

    public int getOperation() {
        return operation;
    }

    public static int getOperationIdByName(String goal) {
        return Arrays.stream(values())
                .filter(paymentGoal -> paymentGoal
                        .name()
                        .equalsIgnoreCase(goal))
                .findFirst()
                .orElse(DEFAULT)
                .getOperation();
    }
}
