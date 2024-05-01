package Backend.socket.domain.chat.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatMessageResponseDto {
    private String receivedUser;
    private List<String> sessionList;
    private ChatMessageElementResponseDto message;

    public static ChatMessageResponseDto of(String receivedUser, List<String> sessionList, ChatMessageElementResponseDto message) {
        return ChatMessageResponseDto.builder()
                .receivedUser(receivedUser)
                .sessionList(sessionList)
                .message(message)
                .build();
    }
}
