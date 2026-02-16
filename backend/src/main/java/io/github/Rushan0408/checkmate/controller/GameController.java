package io.github.Rushan0408.checkmate.controller;

import java.security.Principal;

import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import io.github.Rushan0408.checkmate.dto.websocket.MoveDto;
import io.github.Rushan0408.checkmate.service.GameService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @MessageMapping("/game/move")
    public void handleMove(MoveDto move, Principal principal) {
        System.out.println("\n"+move+"\n");
        if (principal == null) {
            throw new MessagingException("Unauthenticated WebSocket message");
        }
        gameService.makeMove(move, principal);
    }

    @MessageMapping("/game/possibleMoves")
    public void findAllPossibleMoves(MoveDto move, Principal principal ) {
        System.out.println("\n called Possible Move " + move + "\n");
        gameService.findAllPossibleMoves(move,principal);
    }
}

