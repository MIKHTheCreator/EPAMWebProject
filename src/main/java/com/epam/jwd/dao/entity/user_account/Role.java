package com.epam.jwd.dao.entity.user_account;

public enum Role {
    UNAUTHORIZED(0), USER(1), ADMIN(2);

    private final Integer roleId;

    Role(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
