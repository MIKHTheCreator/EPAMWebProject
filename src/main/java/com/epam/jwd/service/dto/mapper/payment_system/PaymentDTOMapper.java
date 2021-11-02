package com.epam.jwd.service.dto.mapper.payment_system;

import com.epam.jwd.dao.entity.payment_system.Payment;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.payment_system.PaymentDTO;

public class PaymentDTOMapper implements DTOMapper<PaymentDTO, Payment, Integer> {

    @Override
    public PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO.Builder()
                .withId(payment.getId())
                .withDateOfPayment(payment.getDateOfPayment())
                .withPaymentGoal(payment.getPaymentGoal())
                .withPaymentOrganization(payment.getPaymentOrganization())
                .withSumOfPayment(payment.getSumOfPayment())
                .withUserId(payment.getUserid())
                .withBankAccountId(payment.getBankAccountId())
                .build();
    }

    @Override
    public Payment convertToEntity(PaymentDTO paymentDTO) {

        return new Payment.Builder()
                .withId(paymentDTO.getId())
                .withDateOfPayment(paymentDTO.getDateOfPayment())
                .withPaymentGoal(paymentDTO.getPaymentGoal())
                .withPaymentOrganization(paymentDTO.getPaymentOrganization())
                .withSumOfPayment(paymentDTO.getSumOfPayment())
                .withUserId(paymentDTO.getUserid())
                .withBankAccountId(paymentDTO.getBankAccountId())
                .build();
    }
}
