package com.epam.jwd.dao.entity.user_account;

import java.util.Arrays;
import java.util.List;

/**
 * Enum which describes available roles in Bank System
 */
public enum Role {
    UNAUTHORIZED(0), ADMIN(1), USER(2);

    /**
     * Integer field which describes role id
     */
    private final Integer roleId;

    /**
     * List of all available roles of enum
     */
    private static final List<Role> ALL_AVAILABLE_ROLES = Arrays.asList(values());

    Role(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public static List<Role> valuesAsList() {
        return ALL_AVAILABLE_ROLES;
    }
}
