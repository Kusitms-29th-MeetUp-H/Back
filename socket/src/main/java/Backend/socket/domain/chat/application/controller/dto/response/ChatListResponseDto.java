package Backend.socket.domain.chat.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatListResponseDto {
    private List<UserChatResponseDto> chatList;

    public static ChatListResponseDto of(List<UserChatResponseDto> chatList) {
        return ChatListResponseDto.builder()
                .chatList(chatList)
                .build();
    }
}
