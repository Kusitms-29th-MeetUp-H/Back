package Backend.socket.global.error.handler;

import Backend.socket.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageErrorHandler extends StompSubProtocolErrorHandler {
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> errorMessage(ErrorCode errorCode) {
        String code = String.valueOf(errorCode.getMessage());
        StompHeaderAccessor accessor = getStompHeaderAccessor(errorCode);
        return MessageBuilder.createMessage(code.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }

    private StompHeaderAccessor getStompHeaderAccessor(ErrorCode errorCode) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setMessage(String.valueOf(errorCode.getMessage()));
        accessor.setLeaveMutable(true);
        return accessor;
    }
}
