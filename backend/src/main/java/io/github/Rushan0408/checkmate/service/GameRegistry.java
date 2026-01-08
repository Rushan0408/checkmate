package io.github.Rushan0408.checkmate.service;

import org.springframework.stereotype.Service;

import io.github.Rushan0408.checkmate.model.Room;

import java.util.*;
import java.util.concurrent.*;

@Service
public class GameRegistry {

    public final Map<String, Room> rooms = new ConcurrentHashMap<>();
    public final Map<String, String> playerToRoom = new ConcurrentHashMap<>();
    public final Queue<String> matchmakingQueue = new ConcurrentLinkedQueue<>();

    public synchronized Room queuePlayer(String playerId) {
        if (matchmakingQueue.isEmpty()) {
            matchmakingQueue.add(playerId);
            return null;
        }
        String opponentId = matchmakingQueue.poll();
        String roomId = UUID.randomUUID().toString();
        Room room = new Room(roomId, opponentId, playerId);
        rooms.put(roomId, room);
        playerToRoom.put(playerId, roomId);
        playerToRoom.put(opponentId, roomId);
        return room;
    }

    public Room getRoomByPlayer(String playerId) {
        String roomId = playerToRoom.get(playerId);
        return roomId == null ? null : rooms.get(roomId);
    }
}



