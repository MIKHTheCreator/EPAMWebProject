package com.epam.jwd.dao.entity;

import java.time.LocalDate;
import java.util.Objects;

public class CreditCard extends AbstractEntity<Integer> {

    private Integer creditCardNumber;
    private LocalDate creditCardExpiration;
    private String nameAndSurname;
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

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return creditCardNumber.equals(that.creditCardNumber)
                && creditCardExpiration.equals(that.creditCardExpiration)
                && nameAndSurname.equals(that.nameAndSurname)
                && CVV.equals(that.CVV)
                && password.equals(that.password)
                && bankAccount.equals(that.bankAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditCardNumber, creditCardExpiration, nameAndSurname, CVV, password, bankAccount);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCardNumber=" + creditCardNumber +
                ", creditCardExpiration=" + creditCardExpiration +
                ", nameAndSurname='" + nameAndSurname + '\'' +
                ", CVV=" + CVV +
                ", password=" + password +
                ", bankAccount=" + bankAccount +
                '}';
    }

    public static class Builder {

        private Integer id;
        private Integer creditCardNumber;
        private LocalDate creditCardExpiration;
        private String nameAndSurname;
        private Integer CVV;
        private Integer password;
        private BankAccount bankAccount;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCreditCardNumber(Integer creditCardNumber) {
            this.creditCardNumber = creditCardNumber;
            return this;
        }

        public Builder withCreditCardExpiration(LocalDate creditCardExpiration) {
            this.creditCardExpiration = creditCardExpiration;
            return this;
        }

        public Builder withNameAndSurname(String nameAndSurname) {
            this.nameAndSurname = nameAndSurname;
            return this;
        }

        public Builder withCVV(Integer CVV) {
            this.CVV = CVV;
            return this;
        }

        public Builder withPassword(Integer password) {
            this.password = password;
            return this;
        }

        public Builder withBankAccount(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        public CreditCard build() {
            CreditCard creditCard = new CreditCard();
            creditCard.setId(this.id);
            creditCard.setCreditCardNumber(this.creditCardNumber);
            creditCard.setCreditCardExpiration(this.creditCardExpiration);
            creditCard.setNameAndSurname(this.nameAndSurname);
            creditCard.setCVV(this.CVV);
            creditCard.setPassword(this.password);

            return creditCard;
        }
    }
}
