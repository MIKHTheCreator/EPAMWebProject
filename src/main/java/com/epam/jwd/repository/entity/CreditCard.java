package com.epam.jwd.repository.entity;

import java.time.LocalDate;
import java.util.Objects;

public class CreditCard extends AbstractEntity<Integer> {

    private Integer creditCardNumber;
    private LocalDate creditCardExpiration;
    private Integer CVV;
    private Integer password;
    private BankAccount bankAccount;

    public CreditCard() {
    }


    public CreditCard(Integer id) {
        super(id);
    }

    public CreditCard(Integer id, Integer creditCardNumber, LocalDate creditCardExpiration,
                      Integer CVV, Integer password, BankAccount bankAccount) {
        super(id);
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiration = creditCardExpiration;
        this.CVV = CVV;
        this.password = password;
        this.bankAccount = bankAccount;
    }

    public Integer getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Integer creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public LocalDate getCreditCardExpiration() {
        return creditCardExpiration;
    }

    public void setCreditCardExpiration(LocalDate creditCardExpiration) {
        this.creditCardExpiration = creditCardExpiration;
    }

    public Integer getCVV() {
        return CVV;
    }

    public void setCVV(Integer CVV) {
        this.CVV = CVV;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return creditCardNumber.equals(that.creditCardNumber)
                && creditCardExpiration.equals(that.creditCardExpiration)
                && CVV.equals(that.CVV)
                && password.equals(that.password)
                && bankAccount.equals(that.bankAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditCardNumber, creditCardExpiration, CVV, password, bankAccount);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCardNumber=" + creditCardNumber +
                ", creditCardExpiration=" + creditCardExpiration +
                ", CVV=" + CVV +
                ", password=" + password +
                ", bankAccount=" + bankAccount +
                '}';
    }
}
