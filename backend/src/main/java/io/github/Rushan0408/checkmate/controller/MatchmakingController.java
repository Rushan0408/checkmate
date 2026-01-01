package io.github.Rushan0408.checkmate.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import io.github.Rushan0408.checkmate.service.MatchmakingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MatchmakingController {
    
    private final MatchmakingService matchmakingservice;

    @MessageMapping("/matchmaking")
    public void matchmaking ( Principal principal ) {
        System.out.println("\n\n\n\nmatchmaking called");
        String playerUsername = principal.getName();
        matchmakingservice.processMatchmaking(playerUsername);
    }

}
