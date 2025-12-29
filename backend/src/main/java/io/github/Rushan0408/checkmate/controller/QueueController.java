package io.github.Rushan0408.checkmate.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.Rushan0408.checkmate.model.Player;

@RestController
@RequestMapping("/queue")
public class QueueController {
    @PostMapping
    public String joinQueue(Authentication auth) {
        Player player = (Player) auth.getPrincipal();
        String id = player.getId();
        return "queue called";
    }
}