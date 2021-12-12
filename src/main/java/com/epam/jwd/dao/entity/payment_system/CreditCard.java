package com.epam.jwd.dao.entity.payment_system;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author mikh
 * CreditCard entity class which provides user with Credit Card entity and optional fields
 * This class extends AbstractEntity and uses Integer id field
 */
public class CreditCard extends AbstractEntity<Integer> {

    /**
     * String which associated with credit card number
     */
    private String number;
    /**
     * LocalDate field with credit card expiration date
     * @see LocalDate
     */
    private LocalDate expirationDate;
    /**
     * String filed with full name
     */
    private String fullName;
    /**
     * String field with cvv code
     */
    private String CVV;
    /**
     * String field with pin code
     */
    private String pin;
    /**
     * Integer field with bankAccountId
     */
    private Integer bankAccountId;
    /**
     * Integer field with userId
     */
    private Integer userId;

    /**
     * Constructor without arguments for creating empty CreditCard object
     * @see CreditCard#CreditCard(Integer)
     * @see CreditCard#CreditCard(Integer, String, LocalDate, String, String, String, Integer, Integer)
     */
    public CreditCard() {
    }

    /**
     * Constructor with id argument
     * @see AbstractEntity#AbstractEntity(Object)
     * @param id provided credit card id
     */
    public CreditCard(Integer id) {
        super(id);
    }

    /**
     * Constructor with all possible arguments
     * @param id provided credit card id
     * @param number credit card number
     * @param expirationDate credit card expiration date
     * @param fullName users full name
     * @param CVV credit card cvv code
     * @param pin credit card pin code
     * @param bankAccount bank account associated with current credit card
     * @param userId user associated with current credit card
     */
    public CreditCard(Integer id, String number, LocalDate expirationDate, String fullName,
                      String CVV, String pin, Integer bankAccount, Integer userId) {
        super(id);
        this.number = number;
        this.expirationDate = expirationDate;
        this.fullName = fullName;
        this.CVV = CVV;
        this.pin = pin;
        this.bankAccountId = bankAccount;
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
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
        private String number;
        private LocalDate expirationDate;
        private String fullName;
        private String CVV;
        private String pin;
        private Integer bankAccountId;
        private Integer userId;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withNumber(String number) {
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

        public Builder withCVV(String CVV) {
            this.CVV = CVV;
            return this;
        }

        public Builder withPin(String pin) {
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
