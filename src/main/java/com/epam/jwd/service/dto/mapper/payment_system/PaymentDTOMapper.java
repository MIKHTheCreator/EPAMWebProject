package com.epam.jwd.service.dto.mapper.payment_system;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.entity.payment_system.Payment;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.payment_system.PaymentDTO;

/**
 * PaymentDTOMapper class which implements DTOMapper for PaymentDTO and Payment entity
 * with Integer id
 *
 * @author mikh
 * @see DTOMapper
 */
public class PaymentDTOMapper implements DTOMapper<PaymentDTO, Payment, Integer> {

    /**
     * Method for converting PaymentDTO to Payment
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
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

    /**
     * Method for converting Payment to PaymentDTO
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
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
