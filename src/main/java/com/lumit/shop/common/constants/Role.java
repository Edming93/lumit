package com.lumit.shop.common.constants;

public enum Role {
    SUPER_ADMIN("ROLE_SUPER"), ADMIN("ROLE_ADMIN"), USER("USER");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
