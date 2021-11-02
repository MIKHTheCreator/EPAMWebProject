package com.epam.jwd.service.dto.mapper.payment_system;

import com.epam.jwd.dao.entity.payment_system.CreditCard;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.payment_system.CreditCardDTO;

public class CreditCardDTOMapper implements DTOMapper<CreditCardDTO, CreditCard, Integer> {

    @Override
    public CreditCardDTO convertToDTO(CreditCard creditCard) {
        return new CreditCardDTO.Builder()
                .withId(creditCard.getId())
                .withNumber(creditCard.getNumber())
                .withExpirationDate(creditCard.getExpirationDate())
                .withFullName(creditCard.getFullName())
                .withCVV(creditCard.getCVV())
                .withPin(creditCard.getPin())
                .withBankAccountId(creditCard.getBankAccountId())
                .withUserId(creditCard.getUserId())
                .build();
    }

    @Override
    public CreditCard convertToEntity(CreditCardDTO creditCardDTO) {

        return new CreditCard.Builder()
                .withId(creditCardDTO.getId())
                .withNumber(creditCardDTO.getNumber())
                .withExpirationDate(creditCardDTO.getExpirationDate())
                .withFullName(creditCardDTO.getFullName())
                .withCVV(creditCardDTO.getCVV())
                .withPin(creditCardDTO.getPin())
                .withBankAccountId(creditCardDTO.getBankAccountId())
                .withUserId(creditCardDTO.getUserId())
                .build();
    }
}
