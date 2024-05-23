package Backend.socket.api.chat.controller;


import Backend.socket.api.chat.service.dto.request.ChatMessageRoomRequestDto;
import Backend.socket.api.chat.service.ChatService;
import Backend.socket.global.common.MessageSuccessCode;
import Backend.socket.global.common.MessageSuccessResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;
    private final RedisTemplate redisTemplate;
    public ChatController(ChatService chatService, SimpMessagingTemplate template,
                          @Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        this.chatService = chatService;
        this.template = template;
        this.redisTemplate = redisTemplate;
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
//    @MessageMapping("/room/image/{roomName}")
//    @SendTo("/sub/room/{roomName}")
//    public MessageSuccessResponse sendImageMessageInRoom(@DestinationVariable("roomName") final String roomName,
//                                                         @RequestBody final ChatMessageRoomRequestDto chatMessageRoomRequestDto) throws IOException {
//        return MessageSuccessResponse.of(MessageSuccessCode.RECEIVED, chatService.createSendMessageContentInRoom(roomName, chatMessageRoomRequestDto).getMessage());
//    }
//    @MessageMapping("/room/image/{roomName}")
//    @SendTo("/sub/room/{roomName}")
//    public MessageSuccessResponse sendImageMessageInRoom(@DestinationVariable("roomName") final String roomName,
//                                                        @RequestBody final image image) throws IOException {
//        return MessageSuccessResponse.of(MessageSuccessCode.RECEIVED, chatService.createSendImageContentInRoom(roomName, image).getMessage());
//    }
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