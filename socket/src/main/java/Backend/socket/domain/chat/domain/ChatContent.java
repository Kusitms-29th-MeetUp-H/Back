package Backend.socket.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChatContent {
    private String userName;
    private String content;
    private LocalDateTime time;

    public static ChatContent createChatContent(String userName, String content, Chat chat) {
        ChatContent chatContent = ChatContent.builder()
                .userName(userName)
                .content(content)
                .time(LocalDateTime.now())
                .build();
        chat.addChatContent(chatContent);
        return chatContent;
    }
    public static ChatContent createChatContent(String userName, String content, Room room) {
        ChatContent chatContent = ChatContent.builder()
                .userName(userName)
                .content(content)
                .time(LocalDateTime.now())
                .build();
        room.addChatContent(chatContent);
        return chatContent;
    }
}
