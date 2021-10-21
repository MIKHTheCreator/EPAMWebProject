package com.epam.jwd.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Payment extends AbstractEntity<Integer> {

    private BigDecimal sumOfPayment;
    private LocalDate dateOfPayment;
    private String paymentOrganization;
    private String paymentGoal;
    private Integer bankAccountId;

    public Payment() {
    }

    public Payment(Integer id) {
        super(id);
    }

    public Payment(Integer id, BigDecimal sumOfPayment, LocalDate dateOfPayment,
                   String paymentOrganization, String paymentGoal, Integer bankAccountId) {
        super(id);
        this.sumOfPayment = sumOfPayment;
        this.dateOfPayment = dateOfPayment;
        this.paymentOrganization = paymentOrganization;
        this.paymentGoal = paymentGoal;
        this.bankAccountId = bankAccountId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return sumOfPayment.equals(payment.sumOfPayment)
                && dateOfPayment.equals(payment.dateOfPayment)
                && paymentOrganization.equals(payment.paymentOrganization)
                && paymentGoal.equals(payment.paymentGoal)
                && bankAccountId.equals(payment.bankAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sumOfPayment, dateOfPayment, paymentOrganization, paymentGoal, bankAccountId);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "sumOfPayment=" + sumOfPayment +
                ", dateOfPayment=" + dateOfPayment +
                ", paymentOrganization='" + paymentOrganization + '\'' +
                ", paymentGoal='" + paymentGoal + '\'' +
                ", bankAccountId=" + bankAccountId +
                '}';
    }
}
