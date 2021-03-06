package com.epam.jwd.service.dto.mapper.payment_system;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.entity.payment_system.BankAccount;
import com.epam.jwd.dao.entity.payment_system.CreditCard;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.impl.BankAccountDAOImpl;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.dto.payment_system.CreditCardDTO;

/**
 * CreditCardDTOMapper class which implements DTOMapper for CreditCardDTO and CreditCard entity
 * with Integer id
 *
 * @author mikh
 * @see DTOMapper
 */
public class CreditCardDTOMapper implements DTOMapper<CreditCardDTO, CreditCard, Integer> {

    private final DAO<BankAccount, Integer> bankAccountDAO = BankAccountDAOImpl.getInstance();
    private final DTOMapper<BankAccountDTO, BankAccount, Integer> bankAccountMapper = new BankAccountDTOMapper();

    /**
     * Method for converting CreditCardDTO to CreditCard
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
    @Override
    public CreditCardDTO convertToDTO(CreditCard creditCard) throws DAOException {
        return new CreditCardDTO.Builder()
                .withId(creditCard.getId())
                .withNumber(creditCard.getNumber())
                .withExpirationDate(creditCard.getExpirationDate())
                .withFullName(creditCard.getFullName())
                .withCVV(creditCard.getCVV())
                .withPin(creditCard.getPin())
                .withBankAccount(bankAccountMapper.convertToDTO(bankAccountDAO.findById(creditCard.getBankAccountId())))
                .withUserId(creditCard.getUserId())
                .build();
    }

    /**
     * Method for converting CreditCard to CreditCardDTO
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
    @Override
    public CreditCard convertToEntity(CreditCardDTO creditCardDTO) {

        return new CreditCard.Builder()
                .withId(creditCardDTO.getId())
                .withNumber(creditCardDTO.getNumber())
                .withExpirationDate(creditCardDTO.getExpirationDate())
                .withFullName(creditCardDTO.getFullName())
                .withCVV(creditCardDTO.getCVV())
                .withPin(creditCardDTO.getPin())
                .withBankAccountId(creditCardDTO.getBankAccount().getId())
                .withUserId(creditCardDTO.getUserId())
                .build();
    }
}
