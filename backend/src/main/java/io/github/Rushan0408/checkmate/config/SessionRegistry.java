package io.github.Rushan0408.checkmate.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class SessionRegistry {

    private final Map<String, String> sessionIdToUsername = new ConcurrentHashMap<>();

    public void register(String sessionId, String username) {
        sessionIdToUsername.put(sessionId, username);
    }

    public String getUsername(String sessionId) {
        return sessionIdToUsername.get(sessionId);
    }

    public void remove(String sessionId) {
        sessionIdToUsername.remove(sessionId);
    }
}
