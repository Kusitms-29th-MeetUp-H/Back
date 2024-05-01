package Backend.socket.global.common;

import Backend.socket.global.error.socketException.UnauthorizedException;
import Backend.socket.infra.config.auth.UserAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;

import static Backend.socket.global.error.ErrorCode.INVALID_ACCESS_TOKEN_VALUE;


@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 50)
@Component
public class AuthenticationInterceptor implements ChannelInterceptor {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        StompCommand command = accessor.getCommand();
//        if (!command.equals(StompCommand.CONNECT))
//            return message;
//        String accessToken = getAccessTokenFromHeader(accessor);
//        validateJwtAccessToken(accessToken);
//        Long userId = getUserIdFromAccessToken(accessToken);
//        setAuthentication(accessor, userId);
        return message;
    }

    private String getAccessTokenFromHeader(StompHeaderAccessor accessor) {
        String accessToken = String.valueOf(accessor.getFirstNativeHeader(AUTHORIZATION));
        if (!(StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER)))
            throw new UnauthorizedException(INVALID_ACCESS_TOKEN_VALUE);
        return accessToken.substring(BEARER.length());
    }

    private void setAuthentication(StompHeaderAccessor headerAccessor, Long userId) {
        UsernamePasswordAuthenticationToken authentication = new UserAuthentication(userId, null, Collections.singleton((GrantedAuthority) () -> AUTHORIZATION));
        headerAccessor.setUser(authentication);
    }

    private Long getUserIdFromAccessToken(String accessToken) {
        return jwtProvider.getSubject(accessToken);
    }

    private void validateJwtAccessToken(String accessToken) {
        jwtProvider.validateAccessToken(accessToken);
    }
}
