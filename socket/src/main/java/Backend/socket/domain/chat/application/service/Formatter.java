package Backend.socket.domain.chat.application.service;

import Backend.socket.domain.chat.domain.ChatUser;
import Backend.socket.domain.chat.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Formatter {
    private final MongoTemplate mongoTemplate;
    public ChatUser findChatUserByRoomNameAndUserName(String roomName, String userName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomName").is(roomName).and("chatUserList.name").is(userName));
        Room room = mongoTemplate.findOne(query, Room.class);
        if (room != null) {
            for (ChatUser chatUser : room.getChatUserList()) {
                if (chatUser.getName().equals(userName)) {
                    return chatUser;
                }
            }
        }

        return ChatUser.builder().build();
    }
}
