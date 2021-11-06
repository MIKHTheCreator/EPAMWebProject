package com.epam.jwd.service.dto.payment_system;

import com.epam.jwd.service.dto.AbstractEntityDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PaymentDTO extends AbstractEntityDTO<Integer> {

    private BigDecimal sumOfPayment;
    private Integer billPerCent;
    private BigDecimal totalPaymentSum;
    private LocalDate dateOfPayment;
    private String paymentOrganization;
    private String paymentGoal;
    private Integer bankAccountId;
    private Integer userid;

    public PaymentDTO() {
    }

    public PaymentDTO(Integer id) {
        super(id);
    }

    public PaymentDTO(Integer id, BigDecimal sumOfPayment, Integer billPerCent, BigDecimal totalPaymentSum, LocalDate dateOfPayment,
                      String paymentOrganization, String paymentGoal, Integer bankAccountId, Integer userid) {
        super(id);
        this.sumOfPayment = sumOfPayment;
        this.billPerCent = billPerCent;
        this.totalPaymentSum = totalPaymentSum;
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

    public Integer getBillPerCent() {
        return billPerCent;
    }

    public void setBillPerCent(Integer billPerCent) {
        this.billPerCent = billPerCent;
    }

    public BigDecimal getTotalPaymentSum() {
        return totalPaymentSum;
    }

    public void setTotalPaymentSum(BigDecimal totalPaymentSum) {
        this.totalPaymentSum = totalPaymentSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDTO that = (PaymentDTO) o;
        return sumOfPayment.equals(that.sumOfPayment)
                && billPerCent.equals(that.billPerCent)
                && totalPaymentSum.equals(that.totalPaymentSum)
                && dateOfPayment.equals(that.dateOfPayment)
                && paymentOrganization.equals(that.paymentOrganization)
                && paymentGoal.equals(that.paymentGoal)
                && bankAccountId.equals(that.bankAccountId)
                && userid.equals(that.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sumOfPayment, billPerCent, totalPaymentSum, dateOfPayment, paymentOrganization, paymentGoal, bankAccountId, userid);
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "sumOfPayment=" + sumOfPayment +
                ", billPerCent=" + billPerCent +
                ", totalPaymentSum=" + totalPaymentSum +
                ", dateOfPayment=" + dateOfPayment +
                ", paymentOrganization='" + paymentOrganization + '\'' +
                ", paymentGoal='" + paymentGoal + '\'' +
                ", bankAccountId=" + bankAccountId +
                ", userid=" + userid +
                '}';
    }

    public static class Builder {

        private Integer id;
        private BigDecimal sumOfPayment;
        private Integer billPerCent;
        private BigDecimal totalPaymentSum;
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

        public Builder withBillPerCent(Integer billPerCent) {
            this.billPerCent = billPerCent;
            return this;
        }

        public Builder withTotalPaymentSum(BigDecimal totalPaymentSum) {
            this.totalPaymentSum = totalPaymentSum;
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

        public PaymentDTO build() {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(this.id);
            paymentDTO.setSumOfPayment(this.sumOfPayment);
            paymentDTO.setDateOfPayment(this.dateOfPayment);
            paymentDTO.setPaymentOrganization(this.paymentOrganization);
            paymentDTO.setPaymentGoal(this.paymentGoal);
            paymentDTO.setBankAccountId(this.bankAccountId);
            paymentDTO.setUserid(this.userid);

            return paymentDTO;
        }
    }
}
