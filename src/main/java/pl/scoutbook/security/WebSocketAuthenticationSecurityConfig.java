package pl.scoutbook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationSecurityConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Autowired
    private WebSocketAuthenticatorService webSocketAuthenticatorService;

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        // Endpoints are already registered on WebSocketConfig, no need to add more.
    }

    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
//        registration.setInterceptors(new AuthChannelInterceptorAdapter(this.webSocketAuthenticatorService));
        registration.interceptors(new AuthChannelInterceptorAdapter(this.webSocketAuthenticatorService));
    }

}