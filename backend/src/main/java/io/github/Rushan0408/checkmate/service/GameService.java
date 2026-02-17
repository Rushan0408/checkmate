package io.github.Rushan0408.checkmate.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import io.github.Rushan0408.checkmate.repository.PlayerRepository;
import io.github.Rushan0408.checkmate.dto.websocket.MoveDto;
import io.github.Rushan0408.checkmate.dto.websocket.PossibleMoveDto;
import io.github.Rushan0408.checkmate.model.Player;
import io.github.Rushan0408.checkmate.model.Room;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.github.bhlangonijr.chesslib.Piece;
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

        Move chessMove;

        Square from = Square.fromValue(move.from().toUpperCase());
        Square to   = Square.fromValue(move.to().toUpperCase());

        if (move.promotion() == null) {
            chessMove = new Move(from, to);
        } else {
            boolean isWhite = room.isWhitePlayer(playerId);
            Piece promotion = switch (move.promotion().toLowerCase()) {
                case "q" -> isWhite ? Piece.WHITE_QUEEN : Piece.BLACK_QUEEN;
                case "r" -> isWhite ? Piece.WHITE_ROOK  : Piece.BLACK_ROOK;
                case "b" -> isWhite ? Piece.WHITE_BISHOP: Piece.BLACK_BISHOP;
                case "n" -> isWhite ? Piece.WHITE_KNIGHT: Piece.BLACK_KNIGHT;
                default -> throw new IllegalArgumentException("Invalid promotion");
            };
            chessMove = new Move(from, to, promotion);
        }

        room.makeMove( playerId , chessMove );

        String fen = room.getGameState().getFen();
        Player whitePlayer = playerRepository.findById(room.getWhitePlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        String whitePlayerUsername = whitePlayer.getUsername();

        Player blackPlayer = playerRepository.findById(room.getBlackPlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        String blackPlayerUsername = blackPlayer.getUsername();

        template.convertAndSendToUser(
            whitePlayerUsername,
            "/queue/game/move",
            Map.of(
                "newMove", true,
                "fen", fen
            )
        );
        template.convertAndSendToUser(
            blackPlayerUsername,
            "/queue/game/move",
            Map.of(
                "newMove", true,
                "fen", fen
            )
        );
    }

    public void findAllPossibleMoves(MoveDto move , Principal principal) {
        System.out.println("Principal : " + principal);
        String playerUsername = principal.getName();
        Player player = playerRepository.findByUsername(playerUsername).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        String playerId = player.getId();
        Room room = gameRegistry.getRoomByPlayer(playerId);

        Square from = Square.fromValue(move.from().toUpperCase());
        
        List<PossibleMoveDto> possibleMoveDtos =  room.findAllPossibleMoves(from);

        template.convertAndSendToUser(
            playerUsername,
            "/queue/game/possibleMoves",
            possibleMoveDtos
        );
    }
}

