package pl.scoutbook.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

public class AuthChannelInterceptorAdapter extends ChannelInterceptorAdapter {
    private static final String USERNAME_HEADER = "login";
    private static final String PASSWORD_HEADER = "passcode";
    private static final String USER_LOGIN_HEADER = "userLogin";
    private static final String USER_PASSWORD_HEADER = "userPassword";
    private static final String USER_ID_HEADER = "userId";
    private final WebSocketAuthenticatorService webSocketAuthenticatorService;

    public AuthChannelInterceptorAdapter(final WebSocketAuthenticatorService webSocketAuthenticatorService) {
        this.webSocketAuthenticatorService = webSocketAuthenticatorService;
    }

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
            final String password = accessor.getFirstNativeHeader(PASSWORD_HEADER);
            final String userEmail = accessor.getFirstNativeHeader(USER_LOGIN_HEADER);
            final String userPassword = accessor.getFirstNativeHeader(USER_PASSWORD_HEADER);
            final String userId = accessor.getFirstNativeHeader(USER_ID_HEADER);
            
            final UsernamePasswordAuthenticationToken user = 
            		webSocketAuthenticatorService.getAuthenticatedOrFail(userEmail, "user"+userId , userPassword);
            accessor.setUser(user);
        } 
        return message;
    }
}