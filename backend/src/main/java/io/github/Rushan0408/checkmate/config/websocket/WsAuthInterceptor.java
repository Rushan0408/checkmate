package io.github.Rushan0408.checkmate.config.websocket;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.github.Rushan0408.checkmate.security.AuthUtil;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WsAuthInterceptor implements ChannelInterceptor {

    private final AuthUtil authUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor acc =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (acc == null) return message;

        if (StompCommand.CONNECT.equals(acc.getCommand())) {
            String authHeader = acc.getFirstNativeHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // System.out.println("\n\n" + "call1" + "\n\n");
                try {
                    String userName = authUtil.getUsernameFromToken(authHeader.substring(7));
                    // System.out.println("\n\n" + userName + "\n\n");
                    UsernamePasswordAuthenticationToken obj = new UsernamePasswordAuthenticationToken(
                        userName,
                        null,
                        List.of()
                    );
                    // System.out.println("\n\n" + obj + "\n\n");
                    acc.setUser(obj);
                } catch (Exception ignored) {
                    // allow unauthenticated connect
                }
            }
        }

        // ✅ THIS IS REQUIRED
        return MessageBuilder.createMessage(
            message.getPayload(),
            acc.getMessageHeaders()
        );
    }
}
