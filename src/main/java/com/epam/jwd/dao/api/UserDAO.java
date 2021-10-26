package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.user_account.User;
import com.epam.jwd.dao.entity.user_account.UserRole;

public interface UserDAO extends DAO<User, Integer> {

    UserRole findRoleById(Integer id) throws InterruptedException;
}
