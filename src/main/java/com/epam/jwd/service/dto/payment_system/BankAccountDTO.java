package com.epam.jwd.service.dto.payment_system;

import com.epam.jwd.service.dto.AbstractEntityDTO;

import java.math.BigDecimal;
import java.util.Objects;

public class BankAccountDTO extends AbstractEntityDTO<Integer> {

    private BigDecimal balance;
    private String currency;
    private boolean blocked;

    public BankAccountDTO() {
    }

    public BankAccountDTO(Integer id) {
        super(id);
    }

    public BankAccountDTO(Integer id, BigDecimal balance, String currency, boolean blocked) {
        super(id);
        this.balance = balance;
        this.currency = currency;
        this.blocked = blocked;
    }

    public BankAccountDTO(BigDecimal starterBalance, String currency, boolean blocked) {
        this.balance = starterBalance;
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
        BankAccountDTO that = (BankAccountDTO) o;
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
        return "BankAccountDTO{" +
                "balance=" + balance +
                ", currency='" + currency + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
