package com.epam.jwd.dao.entity;

public enum UserRole {
    USER(0), ADMIN(1);

    private final Integer roleId;

    UserRole(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
