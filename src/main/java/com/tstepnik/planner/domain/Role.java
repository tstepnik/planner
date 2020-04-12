package com.tstepnik.planner.domain;

import java.util.Optional;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<Role> fromDescription(String description) {
        Role[] values = values();

        for (Role role : values) {
            if (role.getDescription().equals(description)) {
                return Optional.of(role);
            }
        }
        return Optional.empty();
    }
}
