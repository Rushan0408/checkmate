package io.github.Rushan0408.checkmate.security;

import java.security.Principal;

public class CustomPrincipal implements Principal {

    private final String userId;
    private final String username;

    public CustomPrincipal(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String getName() {
        return username;   
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
