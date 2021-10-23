package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.User;
import com.epam.jwd.dao.entity.UserRole;

public interface UserDAO extends DAO<User, Integer> {

    UserRole findRoleById(Integer id) throws InterruptedException;
}
