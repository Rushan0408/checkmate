package io.github.Rushan0408.checkmate.service;

import org.springframework.stereotype.Service;

import io.github.Rushan0408.checkmate.model.Player;
import io.github.Rushan0408.checkmate.model.Room;

import java.util.*;
import java.util.concurrent.*;

@Service
public class GameRegistry {

    private final Map<String, Player> players = new ConcurrentHashMap<>();
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Queue<String> matchmakingQueue = new ConcurrentLinkedQueue<>();

    /* Players */
    public void registerPlayer(Player player) {
        players.put(player.getId(), player);
    }

    /* Matchmaking */
    public synchronized Room queuePlayer(String playerId) {
        if (matchmakingQueue.isEmpty()) {
            matchmakingQueue.add(playerId);
            return null;
        }

        String opponentId = matchmakingQueue.poll();
        String roomId = UUID.randomUUID().toString();

        Room room = new Room(roomId, opponentId, playerId);
        rooms.put(roomId, room);

        return room;
    }

    /* Games */
    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }
}


