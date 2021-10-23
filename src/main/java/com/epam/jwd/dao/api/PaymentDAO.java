package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.entity.Payment;
import com.epam.jwd.dao.entity.User;

import java.util.List;

public interface PaymentDAO extends DAO<Payment, Integer>{

    List<Payment> findAllByBankAccountId(Integer id) throws InterruptedException;
}
