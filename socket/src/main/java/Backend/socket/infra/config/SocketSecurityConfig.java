package Backend.socket.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;

@Configuration
public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry message) {
        message
                .nullDestMatcher().permitAll()
                .simpDestMatchers("/pub/**").permitAll()
                .simpSubscribeDestMatchers("/sub/**").permitAll()
                .anyMessage().permitAll();
//        .nullDestMatcher().permitAll()
//                .simpDestMatchers("/pub/**").authenticated()
//                .simpSubscribeDestMatchers("/sub/**").authenticated()
//                .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
//                .anyMessage().denyAll();
    }
}

