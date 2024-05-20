package Backend.socket.domain.chat.application.controller;

import Backend.socket.domain.chat.application.controller.dto.request.ChatListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageRoomRequestDto;
import Backend.socket.domain.chat.application.controller.dto.response.*;
import Backend.socket.domain.chat.application.service.ChatService;
import Backend.socket.domain.chat.application.service.RoomService;
import Backend.socket.domain.chat.domain.Chat;
import Backend.socket.global.common.MessageSuccessCode;
import Backend.socket.global.common.MessageSuccessResponse;
import Backend.socket.infra.config.auth.UserId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RoomController {
    private final SimpMessagingTemplate template;
    private final RedisTemplate redisTemplate;
    private final RoomService roomService;
    private final ChatService chatService;
    public RoomController(RoomService roomService, SimpMessagingTemplate template, @Qualifier("redisTemplate") RedisTemplate redisTemplate, ChatService chatService) {
        this.roomService = roomService;
        this.template = template;
        this.redisTemplate = redisTemplate;
        this.chatService = chatService;
    }
    @MessageMapping("/room/detail/{roomName}")
    public void sendChatDetailMessage(@DestinationVariable("roomName") final String roomName
                                      ) {
        final RoomMessageListResponseDto responseDto = roomService.sendRoomDetailMessage(roomName);
        template.convertAndSend("/sub/room/" + roomName, MessageSuccessResponse.of(MessageSuccessCode.MESSAGE, responseDto));
    }
    @MessageMapping("/room/all/{sessionId}")
    public void sendUserChatListMessage(@DestinationVariable("sessionId") final String sessionId) {
        final RoomListResponseDto responseDto = roomService.sendUserChatListMessage(sessionId);
        template.convertAndSend("/sub/room/" + sessionId, MessageSuccessResponse.of(MessageSuccessCode.CHATLIST, responseDto));
    }
    @PostMapping("/room/{roomName}")
    public MessageSuccessResponse sendChatMessage(@PathVariable("roomName") String roomName,
                                                @RequestBody final ChatMessageRoomRequestDto chatMessageRoomRequestDto) throws IOException {


        return MessageSuccessResponse.of(MessageSuccessCode.RECEIVED, chatService.createSendMessageContentInRoom(roomName, chatMessageRoomRequestDto).getMessage());
    }
}
