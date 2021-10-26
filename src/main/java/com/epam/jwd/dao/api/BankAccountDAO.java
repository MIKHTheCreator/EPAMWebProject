package com.epam.jwd.dao.api;

public interface BankAccountDAO extends DAO<BankAccount, Integer> {

    BankAccount findBankAccountByCreditCardId(Integer id) throws InterruptedException;
}
