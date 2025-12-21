package io.github.Rushan0408.checkmate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Use a simple broker with a public topic prefix that clients can subscribe to.
        // Matchmaking notifications will be published to `/topic/matches`.
        registry.enableSimpleBroker("/topic");
        // If in the future you add application destinations (e.g. @MessageMapping),
        // you can route them under this prefix.
        registry.setApplicationDestinationPrefixes("/app");
    }
}
