package io.github.Rushan0408.checkmate.service;

import java.security.Principal;
import java.util.Map;

import io.github.Rushan0408.checkmate.repository.PlayerRepository;
import io.github.Rushan0408.checkmate.dto.websocket.MoveDto;
import io.github.Rushan0408.checkmate.model.Player;
import io.github.Rushan0408.checkmate.model.Room;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

    private final SimpMessagingTemplate template;
    private final GameRegistry gameRegistry;
    private final PlayerRepository playerRepository;

    public void makeMove(MoveDto move, Principal principal) {

        String playerUsername = principal.getName(); 
        Player player = playerRepository.findByUsername(playerUsername).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        String playerId = player.getId();
        Room room = gameRegistry.getRoomByPlayer(playerId);

        Move chessMove = new Move(
            Square.fromValue(move.from().toUpperCase()),
            Square.fromValue(move.to().toUpperCase())
        );

        room.makeMove( playerId , chessMove );

        String fen = room.getGameState().getFen();
        System.out.println(fen);

        Player whitePlayer = playerRepository.findById(room.getWhitePlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        String whitePlayerUsername = whitePlayer.getUsername();

        Player blackPlayer = playerRepository.findById(room.getBlackPlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        String blackPlayerUsername = blackPlayer.getUsername();

        template.convertAndSendToUser(
            whitePlayerUsername,
            "/queue/game",
            Map.of(
                "newMove", true,
                "fen", fen
            )
        );
        template.convertAndSendToUser(
            blackPlayerUsername,
            "/queue/game",
            Map.of(
                "newMove", true,
                "fen", fen
            )
        );
    }
}

