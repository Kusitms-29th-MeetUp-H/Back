package Backend.socket.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Document(collection = "chat")
public class Chat {
    @Id
    private String chatId;
    @Builder.Default
    private List<ChatUser> chatUserList = new ArrayList<>();
    @Builder.Default
    private List<ChatContent> chatContentList = new ArrayList<>();

    public static Chat creatChat(ChatUser firstUser, ChatUser secondUser) {
        Chat chat = Chat.builder().build();
        chat.addChatUser(firstUser);
        chat.addChatUser(secondUser);
        return chat;
    }

    public void addChatContent(ChatContent content) {
        this.chatContentList.add(content);
    }

    public void addChatUser(ChatUser chatUser) {
        this.chatUserList.add(chatUser);
    }

}
