package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.Client;

public interface ClientDAO extends DAO<Client, Integer> {

    Client findClientByUserId(Integer id) throws InterruptedException;
}
