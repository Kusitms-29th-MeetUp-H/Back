package Backend.socket.domain.chat.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatMessageRoomResponseDto {
    private String sender;
    private List<String> sessionList;
    private ChatMessageElementResponseDto message;

    public static ChatMessageRoomResponseDto of(String sender, List<String> sessionList, ChatMessageElementResponseDto message) {
        return ChatMessageRoomResponseDto.builder()
                .sender(sender)
                .sessionList(sessionList)
                .message(message)
                .build();
    }
}