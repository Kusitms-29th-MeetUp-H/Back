package Backend.socket.api.room.service.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class RoomListResponseDto {
    private String sessionId;
    private List<RoomChatResponseDto> chatList;

    public static RoomListResponseDto of(String sessionId, List<RoomChatResponseDto> chatList) {
        return RoomListResponseDto.builder()
                .sessionId(sessionId)
                .chatList(chatList)
                .build();
    }
}
