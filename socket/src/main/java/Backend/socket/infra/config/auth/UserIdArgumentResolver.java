package Backend.socket.infra.config.auth;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasUserIdAnnotation = parameter.hasParameterAnnotation(UserId.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasUserIdAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
