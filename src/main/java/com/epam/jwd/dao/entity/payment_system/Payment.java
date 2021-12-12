package com.epam.jwd.dao.entity.payment_system;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Payment entity class extends from AbstractEntity with Integer id field
 * Class describes Payment
 */
public class Payment extends AbstractEntity<Integer> {

    /**
     * BigDecimal field which describes payment sum
     *
     * @see BigDecimal
     */
    private BigDecimal sumOfPayment;
    /**
     * LocalDate field which describes date of payment
     *
     * @see LocalDate
     */
    private LocalDate dateOfPayment;
    /**
     * String field which describes payment organization
     */
    private String paymentOrganization;
    /**
     * String field which describes payment goal
     */
    private String paymentGoal;
    /**
     * Integer field which describes bankAccount id of bank account attached to current payment
     */
    private Integer bankAccountId;
    /**
     * Integer field which describes userId attached to current payment
     */
    private Integer userid;

    /**
     * Constructor without arguments for creating empty Payment object
     *
     * @see Payment#Payment(Integer)
     * @see Payment#Payment(Integer, BigDecimal, LocalDate, String, String, Integer, Integer)
     */
    public Payment() {
    }

    /**
     * Constructor with id field which create object with Integer id
     *
     * @param id Integer payment id
     */
    public Payment(Integer id) {
        super(id);
    }

    /**
     * Constructor with all amount of arguments which creates payment with all properties
     *
     * @param id                  payment id
     * @param sumOfPayment        sum of payment
     * @param dateOfPayment       date of payment
     * @param paymentOrganization payment organization
     * @param paymentGoal         payment goal
     * @param bankAccountId       bank account id
     * @param userid              user id
     */
    public Payment(Integer id, BigDecimal sumOfPayment, LocalDate dateOfPayment,
                   String paymentOrganization, String paymentGoal, Integer bankAccountId, Integer userid) {
        super(id);
        this.sumOfPayment = sumOfPayment;
        this.dateOfPayment = dateOfPayment;
        this.paymentOrganization = paymentOrganization;
        this.paymentGoal = paymentGoal;
        this.bankAccountId = bankAccountId;
        this.userid = userid;
    }

    public BigDecimal getSumOfPayment() {
        return sumOfPayment;
    }

    public void setSumOfPayment(BigDecimal sumOfPayment) {
        this.sumOfPayment = sumOfPayment;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getPaymentOrganization() {
        return paymentOrganization;
    }

    public void setPaymentOrganization(String paymentOrganization) {
        this.paymentOrganization = paymentOrganization;
    }

    public String getPaymentGoal() {
        return paymentGoal;
    }

    public void setPaymentGoal(String paymentGoal) {
        this.paymentGoal = paymentGoal;
    }

    public Integer getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return sumOfPayment.equals(payment.sumOfPayment)
                && dateOfPayment.equals(payment.dateOfPayment)
                && paymentOrganization.equals(payment.paymentOrganization)
                && paymentGoal.equals(payment.paymentGoal)
                && bankAccountId.equals(payment.bankAccountId)
                && userid.equals(payment.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sumOfPayment, dateOfPayment, paymentOrganization, paymentGoal, bankAccountId, userid);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "sumOfPayment=" + sumOfPayment +
                ", dateOfPayment=" + dateOfPayment +
                ", paymentOrganization='" + paymentOrganization + '\'' +
                ", paymentGoal='" + paymentGoal + '\'' +
                ", bankAccountId=" + bankAccountId +
                ", userid=" + userid +
                '}';
    }

    /**
     * Builder for Payment object created by Builder pattern for creating object by parts
     */
    public static class Builder {

        private Integer id;
        private BigDecimal sumOfPayment;
        private LocalDate dateOfPayment;
        private String paymentOrganization;
        private String paymentGoal;
        private Integer bankAccountId;
        private Integer userid;

        public Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withSumOfPayment(BigDecimal sumOfPayment) {
            this.sumOfPayment = sumOfPayment;
            return this;
        }

        public Builder withDateOfPayment(LocalDate dateOfPayment) {
            this.dateOfPayment = dateOfPayment;
            return this;
        }

        public Builder withPaymentOrganization(String paymentOrganization) {
            this.paymentOrganization = paymentOrganization;
            return this;
        }

        public Builder withPaymentGoal(String paymentGoal) {
            this.paymentGoal = paymentGoal;
            return this;
        }

        public Builder withBankAccountId(Integer bankAccountId) {
            this.bankAccountId = bankAccountId;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userid = userId;
            return this;
        }

        public Payment build() {
            Payment payment = new Payment();
            payment.setId(this.id);
            payment.setSumOfPayment(this.sumOfPayment);
            payment.setDateOfPayment(this.dateOfPayment);
            payment.setPaymentOrganization(this.paymentOrganization);
            payment.setPaymentGoal(this.paymentGoal);
            payment.setBankAccountId(this.bankAccountId);
            payment.setUserid(this.userid);

            return payment;
        }
    }
}
