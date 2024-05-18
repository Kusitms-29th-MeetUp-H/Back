package Backend.socket.domain.chat.application.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomChatMessageListReq {
    private String roomSession;
    private String fromUserName;
    private String toRoomName;

}
