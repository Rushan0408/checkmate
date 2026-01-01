package io.github.Rushan0408.checkmate.config.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.github.Rushan0408.checkmate.security.AuthUtil;
import io.github.Rushan0408.checkmate.model.Player;
import io.github.Rushan0408.checkmate.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WsAuthInterceptor implements ChannelInterceptor {

    private final PlayerRepository playerRepository;
    private final AuthUtil authUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor acc = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(acc.getCommand())) {

            String authHeader = acc.getFirstNativeHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return null; // reject connection
            }

            String token = authHeader.substring(7);
            String username = authUtil.getUsernameFromToken(token);

            Player player = playerRepository.findByUsername(username).orElseThrow();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    player.getId(), // 👈 recommend ID
                    null,
                    player.getAuthorities());

            acc.setUser(authentication);
        }

        // 🔴 THIS IS THE CRITICAL LINE
        return MessageBuilder.createMessage(
                message.getPayload(),
                acc.getMessageHeaders());
    }

}
