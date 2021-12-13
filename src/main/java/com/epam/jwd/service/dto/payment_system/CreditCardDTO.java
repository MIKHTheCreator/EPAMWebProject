package com.epam.jwd.service.dto.payment_system;

import com.epam.jwd.service.dto.AbstractEntityDTO;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author mikh
 * CreditCardDTO class for CreditCard entity
 * @see com.epam.jwd.dao.entity.payment_system.CreditCard
 */
public class CreditCardDTO extends AbstractEntityDTO<Integer> {

    private String number;
    private LocalDate expirationDate;
    private String fullName;
    private String CVV;
    private String pin;
    private BankAccountDTO bankAccount;
    private Integer userId;

    public CreditCardDTO() {
    }


    public CreditCardDTO(Integer id) {
        super(id);
    }

    public CreditCardDTO(Integer id, String number, LocalDate expirationDate, String fullName,
                         String CVV, String pin, BankAccountDTO bankAccount, Integer userId) {
        super(id);
        this.number = number;
        this.expirationDate = expirationDate;
        this.fullName = fullName;
        this.CVV = CVV;
        this.pin = pin;
        this.bankAccount = bankAccount;
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
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
        CreditCardDTO that = (CreditCardDTO) o;
        return number.equals(that.number)
                && expirationDate.equals(that.expirationDate)
                && fullName.equals(that.fullName)
                && CVV.equals(that.CVV)
                && pin.equals(that.pin)
                && bankAccount.equals(that.bankAccount)
                && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, expirationDate, fullName, CVV, pin, bankAccount, userId);
    }

    @Override
    public String toString() {
        return "CreditCardDTO{" +
                "number='" + number + '\'' +
                ", expirationDate=" + expirationDate +
                ", fullName='" + fullName + '\'' +
                ", CVV='" + CVV + '\'' +
                ", pin='" + pin + '\'' +
                ", bankAccountId=" + bankAccount +
                ", userId=" + userId +
                '}';
    }

    public static class Builder {

        private Integer id;
        private String number;
        private LocalDate expirationDate;
        private String fullName;
        private String CVV;
        private String pin;
        private BankAccountDTO bankAccount;
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

        public Builder withBankAccount(BankAccountDTO bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public CreditCardDTO build() {
            CreditCardDTO creditCardDTO = new CreditCardDTO();
            creditCardDTO.setId(this.id);
            creditCardDTO.setNumber(this.number);
            creditCardDTO.setExpirationDate(this.expirationDate);
            creditCardDTO.setFullName(this.fullName);
            creditCardDTO.setCVV(this.CVV);
            creditCardDTO.setPin(this.pin);
            creditCardDTO.setBankAccount(this.bankAccount);
            creditCardDTO.setUserId(this.userId);

            return creditCardDTO;
        }
    }
}
