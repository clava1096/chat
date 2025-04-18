package com.clava1096.chat.models.enumpack;

public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MODERATOR;

    public String getAuthority() {
        return name();
    }
}
