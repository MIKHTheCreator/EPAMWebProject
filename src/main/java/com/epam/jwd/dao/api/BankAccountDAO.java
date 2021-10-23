package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.BankAccount;

public interface BankAccountDAO extends DAO<BankAccount, Integer> {

    BankAccount findBankAccountByCreditCardId(Integer id) throws InterruptedException;
}
