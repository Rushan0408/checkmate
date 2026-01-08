package io.github.Rushan0408.checkmate.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import io.github.Rushan0408.checkmate.model.Player;
import io.github.Rushan0408.checkmate.model.Room;
import io.github.Rushan0408.checkmate.repository.PlayerRepository;
import io.github.Rushan0408.checkmate.dto.websocket.MatchFoundMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchmakingService {

    private final GameRegistry gameRegistry;
    private final PlayerRepository playerRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void processMatchmaking(String playerUsername) {

        Player player = playerRepository.findByUsername(playerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        Room room = gameRegistry.queuePlayer(player.getId());

        if (room == null) {
            return;
        }
        String whiteId = room.getWhitePlayerId();
        String blackId = room.getBlackPlayerId();

        Player whitePlayer = playerRepository.findById(whiteId)
                .orElseThrow(() -> new IllegalStateException("White player not found"));

        String whiteUsername = whitePlayer.getUsername();

        Player blackPlayer = playerRepository.findById(blackId)
                .orElseThrow(() -> new IllegalStateException("Black player not found"));

        String blackUsername = blackPlayer.getUsername();
        messagingTemplate.convertAndSendToUser(
                whiteUsername,
                "/queue/matchmaking",
                new MatchFoundMessage("Match Found", "white"));

        messagingTemplate.convertAndSendToUser(
                blackUsername,
                "/queue/matchmaking",
                new MatchFoundMessage("Match Found", "black"));
    }

}
