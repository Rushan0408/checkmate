package io.github.Rushan0408.checkmate.controller;

import java.security.Principal;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import io.github.Rushan0408.checkmate.service.GameService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GameController {
    
    private final GameService gameService;

    @MessageMapping("/game")
    public void handleMove(Message<?> message) {
        Principal principal =
            SimpMessageHeaderAccessor.getUser(message.getHeaders());
        if (principal == null) {
            throw new MessagingException("Unauthenticated WebSocket message");
        }    
        gameService.makeMove(message,principal);
    }

}
