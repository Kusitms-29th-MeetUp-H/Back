package Backend.socket.domain.chat.service;

import Backend.socket.api.chat.service.dto.response.ChatMessageElementResponseDto;
import Backend.socket.api.chat.service.dto.response.ChatMessageRoomResponseDto;
import Backend.socket.global.common.MessageSuccessCode;
import Backend.socket.global.common.MessageSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    public RedisSubscriber(ObjectMapper objectMapper, @Qualifier("redisTemplate") RedisTemplate redisTemplate, SimpMessageSendingOperations messagingTemplate){
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.messagingTemplate = messagingTemplate;
    }

@Override
public void onMessage(Message message, byte[] pattern) {
    String publishMessage = getPublishMessage(message);
    ChatMessageRoomResponseDto messageResponseDto = getChatMessageFromObjectMapper(publishMessage);
    messageResponseDto.getSessionList().forEach(sessionId ->
            sendChatMessage(messageResponseDto.getRoom(), messageResponseDto.getMessage()));
}

private ChatMessageRoomResponseDto getChatMessageFromObjectMapper(String publishMessage) {
    ChatMessageRoomResponseDto messageResponseDto;
    try {
        messageResponseDto = objectMapper.readValue(publishMessage, ChatMessageRoomResponseDto.class);
    } catch (Exception e) {
        throw new MessageDeliveryException("Error");
    }
    return messageResponseDto;
}
    private String getPublishMessage(Message message) {
        return (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
    }
    private void sendChatMessage(String roomName, ChatMessageElementResponseDto message) {
        messagingTemplate.convertAndSend("/sub/room/" + roomName,
                MessageSuccessResponse.of(MessageSuccessCode.RECEIVED, message));
    }

}
