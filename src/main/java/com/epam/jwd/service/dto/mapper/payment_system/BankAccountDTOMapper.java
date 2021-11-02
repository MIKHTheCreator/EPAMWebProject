package com.epam.jwd.service.dto.mapper.payment_system;

import com.epam.jwd.dao.entity.payment_system.BankAccount;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;

public class BankAccountDTOMapper implements DTOMapper<BankAccountDTO, BankAccount, Integer> {

    @Override
    public BankAccountDTO convertToDTO(BankAccount bankAccount) {

        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setBalance(bankAccount.getBalance());
        bankAccountDTO.setCurrency(bankAccount.getCurrency());
        bankAccountDTO.setBlocked(bankAccount.isBlocked());

        return bankAccountDTO;
    }

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
