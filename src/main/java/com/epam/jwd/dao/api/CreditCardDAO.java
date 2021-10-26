package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.payment_system.CreditCard;

import java.util.List;

public interface CreditCardDAO extends DAO<CreditCard, Integer> {

    List<CreditCard> findAllCreditCardsByUserId(Integer id) throws InterruptedException;
}
