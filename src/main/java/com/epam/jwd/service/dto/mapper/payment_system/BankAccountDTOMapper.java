package com.epam.jwd.service.dto.mapper.payment_system;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.entity.payment_system.BankAccount;
import com.epam.jwd.service.dto.AbstractEntityDTO;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;

/**
 * BankAccountDTOMapper class which implements DTOMapper for BankAccountDTO and BankAccount entity
 * with Integer id
 *
 * @author mikh
 * @see DTOMapper
 */
public class BankAccountDTOMapper implements DTOMapper<BankAccountDTO, BankAccount, Integer> {

    /**
     * Method for converting BankAccountDTO to BankAccount
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
    @Override
    public BankAccountDTO convertToDTO(BankAccount bankAccount) {

        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setBalance(bankAccount.getBalance());
        bankAccountDTO.setCurrency(bankAccount.getCurrency());
        bankAccountDTO.setBlocked(bankAccount.isBlocked());

        return bankAccountDTO;
    }

    /**
     * Method for converting BankAccount to BankAccountDTO
     *
     * @see DTOMapper#convertToEntity(AbstractEntityDTO)
     */
    @Override
    public BankAccount convertToEntity(BankAccountDTO bankAccountDTO) {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(bankAccountDTO.getId());
        bankAccount.setBalance(bankAccountDTO.getBalance());
        bankAccount.setCurrency(bankAccountDTO.getCurrency());
        bankAccount.setBlocked(bankAccountDTO.isBlocked());

        return bankAccount;
    }
}
