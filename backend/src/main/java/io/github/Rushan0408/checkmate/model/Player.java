package io.github.Rushan0408.checkmate.model;

import java.util.UUID;

public class Player {

    private String playerId;
    private String username;
    private String sessionId;

    public Player(String username, String sessionId) {
        this.playerId = UUID.randomUUID().toString();
        this.username = username;
        this.sessionId = sessionId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }
}
