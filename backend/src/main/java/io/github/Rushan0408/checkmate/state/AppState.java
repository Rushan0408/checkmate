package io.github.Rushan0408.checkmate.state;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import io.github.Rushan0408.checkmate.dto.websocket.MatchFoundMessage;
import io.github.Rushan0408.checkmate.model.GameRoom;
import io.github.Rushan0408.checkmate.model.Player;

@Component
public class AppState {

    private final Queue<Player> waitingQueue = new ConcurrentLinkedQueue<>();
    private final Map<String, GameRoom> activeGames = new ConcurrentHashMap<>();

    private final SimpMessagingTemplate messagingTemplate;

    public AppState(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public synchronized Optional<GameRoom> addToQueue(Player player) {

        Player waitingPlayer = waitingQueue.poll();

        if (waitingPlayer == null) {
            waitingQueue.add(player);
            return Optional.empty();
        }

        GameRoom gameRoom = new GameRoom(waitingPlayer, player);
        activeGames.put(gameRoom.getGameRoomId(), gameRoom);

        // Broadcast a single match-found event. The payload contains the gameRoomId
        // and both players' usernames so the frontend can decide if the message
        // is relevant to the current client.
        messagingTemplate.convertAndSend(
                "/topic/matches",
                new MatchFoundMessage(
                        gameRoom.getGameRoomId(),
                        waitingPlayer.getUsername(),
                        player.getUsername()
                ));

        return Optional.of(gameRoom);
    }
}
