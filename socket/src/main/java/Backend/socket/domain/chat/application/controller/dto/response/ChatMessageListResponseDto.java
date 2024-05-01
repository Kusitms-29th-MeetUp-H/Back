package Backend.socket.domain.chat.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatMessageListResponseDto {
    private ChatUserResponseDto user;
    private List<ChatMessageElementResponseDto> chatMessageList;

    public static ChatMessageListResponseDto of(ChatUserResponseDto chatUserResponseDto, List<ChatMessageElementResponseDto> chatMessageList) {
        return ChatMessageListResponseDto.builder()
                .user(chatUserResponseDto)
                .chatMessageList(chatMessageList)
                .build();
    }
}
