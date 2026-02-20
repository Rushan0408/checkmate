package io.github.Rushan0408.checkmate.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.Rushan0408.checkmate.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/reconnect")
@RequiredArgsConstructor
public class ReconnectController {

    private final GameService gameService;

    @GetMapping
    public void reconnectGame(Principal principal){
        log.info("incoming request to reconnectGame");
        gameService.reconnectGame(principal);
    }
    
}
