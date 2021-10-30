package com.epam.jwd.dao.entity.payment_system;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.time.LocalDate;
import java.util.Objects;

public class CreditCard extends AbstractEntity<Integer> {

    private Integer number;
    private LocalDate expirationDate;
    private String fullName;
    private Integer CVV;
    private Integer pin;
    private Integer bankAccountId;
    private Integer userId;

    public CreditCard() {
    }


    public CreditCard(Integer id) {
        super(id);
    }

    public CreditCard(Integer id, Integer number, LocalDate expirationDate, String fullName,
                      Integer CVV, Integer pin, Integer bankAccount, Integer userId) {
        super(id);
        this.number = number;
        this.expirationDate = expirationDate;
        this.fullName = fullName;
        this.CVV = CVV;
        this.pin = pin;
        this.bankAccountId = bankAccount;
        this.userId = userId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getCVV() {
        return CVV;
    }

    public void setCVV(Integer CVV) {
        this.CVV = CVV;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Integer getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return number.equals(that.number)
                && expirationDate.equals(that.expirationDate)
                && fullName.equals(that.fullName)
                && CVV.equals(that.CVV)
                && pin.equals(that.pin)
                && bankAccountId.equals(that.bankAccountId)
                && userId.equals(that.userId);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "number=" + number +
                ", expirationDate=" + expirationDate +
                ", fullName='" + fullName + '\'' +
                ", CVV=" + CVV +
                ", pin=" + pin +
                ", bankAccount=" + bankAccountId +
                ", userId=" + userId +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, expirationDate, fullName, CVV, pin, bankAccountId, userId);
    }

    public static class Builder {

        private Integer id;
        private Integer number;
        private LocalDate expirationDate;
        private String fullName;
        private Integer CVV;
        private Integer pin;
        private Integer bankAccountId;
        private Integer userId;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withNumber(Integer number) {
            this.number = number;
            return this;
        }

        public Builder withExpirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder withCVV(Integer CVV) {
            this.CVV = CVV;
            return this;
        }

        public Builder withPin(Integer pin) {
            this.pin = pin;
            return this;
        }

        public Builder withBankAccountId(Integer bankAccountId) {
            this.bankAccountId = bankAccountId;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public CreditCard build() {
            CreditCard creditCard = new CreditCard();
            creditCard.setId(this.id);
            creditCard.setNumber(this.number);
            creditCard.setExpirationDate(this.expirationDate);
            creditCard.setFullName(this.fullName);
            creditCard.setCVV(this.CVV);
            creditCard.setPin(this.pin);
            creditCard.setBankAccountId(this.bankAccountId);
            creditCard.setUserId(this.userId);

            return creditCard;
        }
    }
}
