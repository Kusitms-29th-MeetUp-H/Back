package Backend.socket.domain.chat.application.controller;


import Backend.socket.domain.chat.application.controller.dto.request.ChatListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageRequestDto;
import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageRoomRequestDto;
import Backend.socket.domain.chat.application.controller.dto.response.ChatListResponseDto;
import Backend.socket.domain.chat.application.controller.dto.response.ChatMessageListResponseDto;
import Backend.socket.domain.chat.application.controller.dto.response.ChatMessageResponseDto;
import Backend.socket.domain.chat.application.controller.dto.response.ChatMessageRoomResponseDto;
import Backend.socket.domain.chat.application.service.ChatService;
import Backend.socket.global.common.MessageSuccessCode;
import Backend.socket.global.common.MessageSuccessResponse;
import Backend.socket.global.common.image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;
    private final RedisTemplate redisTemplate;
    public ChatController(ChatService chatService, SimpMessagingTemplate template,
                          @Qualifier("chatRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        this.chatService = chatService;
        this.template = template;
        this.redisTemplate = new RedisTemplate<>();
        this.redisTemplate.setConnectionFactory(connectionFactory);
    }
//    @MessageMapping("/chat/{sessionId}")
//    public void sendChatMessage(@DestinationVariable("sessionId") final String sessionId,
//                                @RequestBody final ChatMessageRequestDto chatMessageRequestDto) {
//        final ChatMessageResponseDto responseDto = chatService.createSendMessageContent(sessionId, chatMessageRequestDto);
//        redisTemplate.convertAndSend("meetingRoom", responseDto);
//    }
    @MessageMapping("/room/{roomName}")
    @SendTo("/sub/room/{roomName}")
    public MessageSuccessResponse sendChatMessageInRoom(@DestinationVariable("roomName") final String roomName,
                                                         @RequestBody final ChatMessageRoomRequestDto chatMessageRoomRequestDto) throws IOException {
        return MessageSuccessResponse.of(MessageSuccessCode.RECEIVED, chatService.createSendMessageContentInRoom(roomName, chatMessageRoomRequestDto).getMessage());
    }
    @MessageMapping("/room/image/{roomName}")
    @SendTo("/sub/room/{roomName}")
    public MessageSuccessResponse sendImageMessageInRoom(@DestinationVariable("roomName") final String roomName,
                                                        @RequestBody final image image) throws IOException {
        return MessageSuccessResponse.of(MessageSuccessCode.RECEIVED, chatService.createSendImageContentInRoom(roomName, image).getMessage());
    }
//    @MessageMapping("/room/{roomName}")
//    public void sendChatMessageInRoom(@DestinationVariable("roomName") final String roomName,
//                                      @RequestBody final ChatMessageRoomRequestDto chatMessageRoomRequestDto) {
//        final ChatMessageRoomResponseDto responseDto = chatService.createSendMessageContentInRoom(roomName, chatMessageRoomRequestDto);
//        redisTemplate.convertAndSend("meetingRoom", responseDto);
//    }

//    @MessageMapping("/chat/detail/{sessionId}")
//    public void sendChatDetailMessage(@DestinationVariable("sessionId") final String sessionId,
//                                      @RequestBody final ChatMessageListRequestDto chatMessageListRequestDto) {
//        final ChatMessageListResponseDto responseDto = chatService.sendChatDetailMessage(sessionId, chatMessageListRequestDto);
//        template.convertAndSend("/sub/chat/" + sessionId, MessageSuccessResponse.of(MessageSuccessCode.MESSAGE, responseDto));
//    }
//
//    @MessageMapping("/chat/all")
//    public void sendUserChatListMessage(@Header("sessionId") final String sessionId,
//                                        @RequestBody final ChatListRequestDto chatListRequestDto) {
//        final ChatListResponseDto responseDto = chatService.sendUserChatListMessage(sessionId, chatListRequestDto);
//        template.convertAndSend("/sub/chat/" + sessionId, MessageSuccessResponse.of(MessageSuccessCode.CHATLIST, responseDto));
//    }

}
