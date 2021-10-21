package com.epam.jwd.dao.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class BankAccount extends AbstractEntity<Integer> {

    private BigDecimal accountBalance;
    private String accountCurrency;
    private List<Payment> payments;
    private boolean isBlocked;

    public BankAccount() {
    }

    public BankAccount(Integer id) {
        super(id);
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return isBlocked == that.isBlocked
                && accountBalance.equals(that.accountBalance)
                && accountCurrency.equals(that.accountCurrency)
                && Objects.equals(payments, that.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountBalance, accountCurrency, payments, isBlocked);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountBalance=" + accountBalance +
                ", accountCurrency='" + accountCurrency + '\'' +
                ", payments=" + payments +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
