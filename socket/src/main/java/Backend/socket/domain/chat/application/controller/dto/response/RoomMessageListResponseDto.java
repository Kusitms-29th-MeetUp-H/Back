package Backend.socket.domain.chat.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class RoomMessageListResponseDto {
    private List<ChatUserResponseDto> users;
    private List<ChatMessageElementResponseDto> chatMessageList;

    public static RoomMessageListResponseDto of(List<ChatUserResponseDto> chatUserResponseDto, List<ChatMessageElementResponseDto> chatMessageList) {
        return RoomMessageListResponseDto.builder()
                .users(chatUserResponseDto)
                .chatMessageList(chatMessageList)
                .build();
    }
}
