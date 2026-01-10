package io.github.Rushan0408.checkmate.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class GameService {

    SimpMessagingTemplate template;

    public void makeMove(Message<?> message , Principal principal){
        //todo
    }
}
