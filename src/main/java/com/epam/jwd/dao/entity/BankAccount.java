package com.epam.jwd.dao.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class BankAccount extends AbstractEntity<Integer> {

    private BigDecimal balance;
    private String currency;
    private boolean blocked;

    public BankAccount() {
    }

    public BankAccount(Integer id) {
        super(id);
    }

    public BankAccount(Integer id, BigDecimal balance, String currency, boolean blocked) {
        super(id);
        this.balance = balance;
        this.currency = currency;
        this.blocked = blocked;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return blocked == that.blocked
                && balance.equals(that.balance)
                && currency.equals(that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, currency, blocked);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                ", currency='" + currency + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
