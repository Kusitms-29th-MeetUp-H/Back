package Backend.socket.domain.chat.application.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatMessageRoomRequestDto {
    private String chatSession;
    private String fromUserName;
    private String toRoomName;
    private String content;
}
