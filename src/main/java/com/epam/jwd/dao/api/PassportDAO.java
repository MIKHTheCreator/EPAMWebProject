package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.PassportData;

public interface PassportDAO extends DAO<PassportData, Integer> {

    PassportData findPassportByUserId(Integer id) throws InterruptedException;
}
