package com.epam.jwd.dao.api;

public interface ClientDAO extends DAO<Client, Integer> {

    Client findClientByUserId(Integer id) throws InterruptedException;
}
