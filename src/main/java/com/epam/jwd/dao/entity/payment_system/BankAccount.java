package com.epam.jwd.dao.entity.payment_system;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Bank Account class which extends AbstractEntity class with Integer id field {@link AbstractEntity}
 * This class provides Bank Account entity with balance, currency fields and shows is this account blocked
 */
public class BankAccount extends AbstractEntity<Integer> {

    /**
     * BigDecimal field which shows bank account balance
     *
     * @see BigDecimal
     */
    private BigDecimal balance;
    /**
     * String field which shows bank account currency
     */
    private String currency;
    /**
     * Boolean field which show is current account blocked or not
     */
    private boolean blocked;

    /**
     * Constructor without arguments which creates empty object
     *
     * @see BankAccount#BankAccount(Integer)
     * @see BankAccount#BankAccount(Integer, BigDecimal, String, boolean)
     */
    public BankAccount() {
    }

    /**
     * Constructor with id argument, which sets id using super class constructor
     *
     * @param id Integer id field
     */
    public BankAccount(Integer id) {
        super(id);
    }

    /**
     * Constructor with full amount of arguments needed to this object
     *
     * @param id       id of the bank account
     * @param balance  balance of bank account
     * @param currency currency of bank account
     * @param blocked  blocked flag
     */
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
