package Backend.socket.domain.chat.application.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatMessageRoomRequestDto {
    private List<String> image;
    private String chatSession;
    private String fromUserName;
    private String toRoomName;
    private String content;
}
