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
        Player player = playerRepository.findByUsername(playerUsername).
                                        orElseThrow( () -> new IllegalArgumentException("Player Not Found : " + playerUsername) );
        Room room = gameRegistry.queuePlayer(player.getId());
        if ( room != null ) {
            String color =  player.getId() == room.getWhitePlayerId()  ? "white" : "black";
            messagingTemplate.convertAndSendToUser(player.getUsername() ,
                                                 "/matchmaking" ,
                                                new MatchFoundMessage("Match Found" , color) ) ;
        }
    }

}
