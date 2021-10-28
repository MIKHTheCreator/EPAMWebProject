package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.user_account.User;

public interface UserDAO extends DAO<User, Integer>{

    User findUserByClientId(Integer clientId);
}
