package Backend.socket.domain.chat.application.controller;

import Backend.socket.domain.chat.application.controller.dto.request.ChatListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.response.ChatListResponseDto;
import Backend.socket.domain.chat.application.controller.dto.response.ChatMessageListResponseDto;
import Backend.socket.domain.chat.application.controller.dto.response.RoomMessageListResponseDto;
import Backend.socket.domain.chat.application.service.ChatService;
import Backend.socket.domain.chat.application.service.RoomService;
import Backend.socket.global.common.MessageSuccessCode;
import Backend.socket.global.common.MessageSuccessResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    private final SimpMessagingTemplate template;
    private final RedisTemplate redisTemplate;
    private final RoomService roomService;
    public RoomController(RoomService roomService, SimpMessagingTemplate template, @Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        this.roomService = roomService;
        this.template = template;
        this.redisTemplate = redisTemplate;
    }
    @MessageMapping("/room/detail/{roomName}")
    public void sendChatDetailMessage(@DestinationVariable("roomName") final String roomName
                                      ) {
        final RoomMessageListResponseDto responseDto = roomService.sendRoomDetailMessage(roomName);
        template.convertAndSend("/sub/room/" + roomName, MessageSuccessResponse.of(MessageSuccessCode.MESSAGE, responseDto));
    }
//    @MessageMapping("/room/all")
//    public void sendUserChatListMessage(@DestinationVariable("roomName") final String roomName,
//                                        @RequestBody final ChatListRequestDto chatListRequestDto) {
//        final ChatListResponseDto responseDto = chatService.sendUserChatListMessage(sessionId, chatListRequestDto);
//        template.convertAndSend("/sub/chat/" + sessionId, MessageSuccessResponse.of(MessageSuccessCode.CHATLIST, responseDto));
//    }
}