package com.epam.jwd.repository.entity;

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
