package io.github.Rushan0408.checkmate.controller;

import java.security.Principal;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import io.github.Rushan0408.checkmate.service.MatchmakingService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MatchmakingController {
    
    private final MatchmakingService matchmakingservice;

@MessageMapping("/matchmaking")
public void matchmaking(Message<?> message) {
    // System.out.println("\n\nmatchmaking controller ran\n\n " + message);

    Principal principal =
        SimpMessageHeaderAccessor.getUser(message.getHeaders());

    

    if (principal == null) {
        throw new MessagingException("Unauthenticated WebSocket message");
    }
    System.out.println("\n\n" + principal.getName() + "\n\n");    
    matchmakingservice.processMatchmaking(principal.getName());
}



}
