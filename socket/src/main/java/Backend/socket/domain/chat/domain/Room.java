package Backend.socket.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Document(collection = "room")
public class Room {
    @Id
    private String roomId;
    private String roomName;
    private String roomSession;
    private String syncName;

    @Builder.Default
    private List<ChatUser> chatUserList = new ArrayList<>();
    @Builder.Default
    private List<ChatContent> chatContentList = new ArrayList<>();

    public static Room createRoom(List<ChatUser> users,String roomName) {
        Room room = Room.builder().
                roomName(roomName).
                build();
        for(ChatUser chatUser : users){
            room.addChatRoom(chatUser);
        }
        return room;
    }
    public static Room createNewRoom(String roomName) {
        Room room = Room.builder()
                .roomName(roomName)
                .build();
        return room;
    }
    public void addChatContent(ChatContent content) {
        this.chatContentList.add(content);
    }

    public void addChatRoom(ChatUser chatUser) {
        this.chatUserList.add(chatUser);
    }
    public Room(String roomId, String roomName, String roomSession, String syncName, List<ChatUser> chatUserList, List<ChatContent> chatContentList) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomSession = roomSession;
        this.syncName = syncName;
        this.chatUserList = chatUserList != null ? chatUserList : new ArrayList<>();
        this.chatContentList = chatContentList != null ? chatContentList : new ArrayList<>();
    }
}
