package Backend.socket.domain.chat.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendMessageResponseDto {
    private String receivedUser;
    private ChatMessageElementResponseDto message;

    public static SendMessageResponseDto of(String receivedUser, ChatMessageElementResponseDto message) {
        return SendMessageResponseDto.builder()
                .receivedUser(receivedUser)
                .message(message)
                .build();
    }
}