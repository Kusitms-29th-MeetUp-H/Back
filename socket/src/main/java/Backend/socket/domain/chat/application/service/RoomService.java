package Backend.socket.domain.chat.application.service;

import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.response.*;
import Backend.socket.domain.chat.domain.Chat;
import Backend.socket.domain.chat.domain.ChatContent;
import Backend.socket.domain.chat.domain.ChatUser;
import Backend.socket.domain.chat.domain.Room;
import Backend.socket.domain.chat.repository.ChatRepository;
import Backend.socket.domain.chat.repository.RoomRepository;
import Backend.socket.domain.chat.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomService {
    private final MongoTemplate mongoTemplate;
    private final RoomRepository roomRepository;
    private final Formatter formatter;
    public RoomMessageListResponseDto sendRoomDetailMessage(String roomName) {
        Room room = getChatByRoomName(roomName);
        List<ChatUserResponseDto> chatUserResponseDto = getChatUserResponseDto(room);
        List<RoomMessageElementResponseDto> chatMessageList = RoomMessageElementResponseDto.listOf(room.getChatContentList(),roomName, formatter::findChatUserByRoomNameAndUserName);
        saveChatRoom(room);
        return RoomMessageListResponseDto.of(chatUserResponseDto, chatMessageList);
    }
    private List<ChatUserResponseDto> getChatUserResponseDto(Room room) {
        List<ChatUser> chatUsers = getChatUserInRoom(room);
        return chatUsers.stream().map(chatUser -> ChatUserResponseDto.of(chatUser)).toList();
    }
    private List<ChatUser> getChatUserInRoom(Room room) {
        //room에 있는 모든 chatuser불러오기
        return room.getChatUserList();
    }
    private Room getChatByRoomName(String roomName) {
        Room room = findRoomChatByRoomName(roomName);
        if (Objects.isNull(room)) {
            // 채팅방이 없는 경우 새로운 채팅방 생성
            room = Room.createNewRoom(roomName);
        }


        return room;
    }

    private Room findRoomChatByRoomName(String roomName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomName").is(roomName));
        return mongoTemplate.findOne(query, Room.class);
    }

    public void saveChatRoom(Room room) {
        roomRepository.save(room);
    }
    public RoomListResponseDto sendUserChatListMessage(String sessionId){
        List<Room> rooms = findRoomListBySession(sessionId);
        List<RoomChatResponseDto> roomChatResponseDtos = createRoomChatResponseDto(rooms);
        roomChatResponseDtos.sort(Comparator.comparing(RoomChatResponseDto::getTime).reversed());
        return RoomListResponseDto.of(sessionId, roomChatResponseDtos);
    }
    private List<RoomChatResponseDto> createRoomChatResponseDto(List<Room> rooms) {
        return rooms.stream()
                .map(room ->
                        RoomChatResponseDto.of(
                                room,
                                getLastChatContent(room.getChatContentList()).getContent(),
                                getLastChatContent(room.getChatContentList()).getTime()))
                .collect(Collectors.toList());
    }
    private ChatContent getLastChatContent(List<ChatContent> chatContentList) {
        return chatContentList.get(chatContentList.size() - 1);
    }
    private List<Room> findRoomListBySession(String sessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatUserList.sessionId").all(sessionId));
        return mongoTemplate.find(query, Room.class);
    }
}
