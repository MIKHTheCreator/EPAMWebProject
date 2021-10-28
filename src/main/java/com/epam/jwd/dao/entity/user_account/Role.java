package com.epam.jwd.dao.entity.user_account;

public enum Role {
    USER(0), ADMIN(1);

    private final Integer roleId;

    Role(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
