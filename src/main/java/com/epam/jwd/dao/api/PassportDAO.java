package com.epam.jwd.dao.api;

public interface PassportDAO extends DAO<PassportData, Integer> {

    PassportData findPassportByUserId(Integer id) throws InterruptedException;
}
