package Backend.socket.domain.chat.application.controller.dto.response;

import Backend.socket.domain.chat.domain.ChatUser;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatUserResponseDto {
    private String sessionId;
    private String name;
    private String profile;

    public static ChatUserResponseDto of(ChatUser chatUser) {
        if (chatUser != null) {
            return ChatUserResponseDto.builder()
                    .sessionId(chatUser.getSessionId())
                    .name(chatUser.getName())
                    .profile(chatUser.getProfile())
                    .build();
        } else {
            return ChatUserResponseDto.builder().build();
        }
    }
}