package Backend.socket.api.chat.service;

import Backend.socket.api.chat.service.dto.request.ChatMessageRoomRequestDto;
import Backend.socket.api.chat.service.dto.response.ChatMessageElementResponseDto;
import Backend.socket.api.chat.service.dto.response.ChatMessageRoomResponseDto;
import Backend.socket.domain.chat.domain.*;
import Backend.socket.domain.chat.repository.RoomRepository;
import Backend.socket.domain.chat.repository.UserRepository;
import Backend.socket.global.error.socketException.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Backend.socket.domain.chat.domain.ChatContent.createChatContent;
import static Backend.socket.global.error.ErrorCode.USER_NOT_FOUND;


@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    public ChatMessageRoomResponseDto createSendMessageContentInRoom(String roomName, ChatMessageRoomRequestDto chatMessageRoomRequestDto) {
        Room room = getChatBySessionsInRoom(roomName, chatMessageRoomRequestDto.getChatSession());
        User user = userRepository.findBySessionId(chatMessageRoomRequestDto.getChatSession()).orElseThrow();
        ChatContent chatContent = createChatContent(chatMessageRoomRequestDto.getFromUserName(), chatMessageRoomRequestDto.getContent(), room, chatMessageRoomRequestDto.getImage());
        ChatMessageElementResponseDto chatMessage = ChatMessageElementResponseDto.of(chatContent, chatMessageRoomRequestDto.getChatSession(), user.getProfile(), chatMessageRoomRequestDto.getImage(), validate(room.getOwnerSession(),chatMessageRoomRequestDto.getChatSession()));
        List<String> sessionIdList = getSessionIdListInRoom(roomName, chatMessageRoomRequestDto.getChatSession());
        saveChatRoom(room);
        return ChatMessageRoomResponseDto.of(chatMessageRoomRequestDto.getToRoomName(), sessionIdList, chatMessage);
    }
    private Boolean validate(String ownerSession, String sessionId){
        if(ownerSession.equals(sessionId))
            return true;
        return false;
    }

    private List<String> getSessionIdListInRoom(String roomName, String sessionId) {
        List<String> sessionList = new ArrayList<>();

        // roomId를 기반으로 Room 문서 찾기
        Room room = findRoomChatByRoomName(roomName);

        if (room != null) {
            // Room에 속한 모든 ChatUser의 sessionId를 리스트에 추가
            for (ChatUser chatUser : room.getChatUserList()) {
                sessionList.add(chatUser.getSessionId());
            }
        }

        return sessionList;
    }


    private Room getChatBySessionsInRoom(String roomName, String sessionId) {
        Room room = findRoomChatByRoomName(roomName);
        if (Objects.isNull(room)) {
            // 채팅방이 없는 경우 새로운 채팅방 생성
            room = createNewRoom(roomName);
        }

        // 채팅방에 sessionId를 가진 유저가 있는지 확인
        if (!isUserExistsInRoom(room, sessionId)) {
            // 유저가 없다면 새로운 유저 생성하여 채팅방에 추가
            ChatUser chatUser = createChatUser(sessionId);
            room.addChatRoom(chatUser);
        }

        return room;
    }

    private Room createNewRoom(String roomName) {
        Room room = Room.builder()
                .roomName(roomName)
                .build();
        return room;
    }
    private boolean isUserExistsInRoom(Room room, String sessionId) {
        for (ChatUser chatUser : room.getChatUserList()) {
            if (chatUser.getSessionId().equals(sessionId)) {
                return true;
            }
        }
        return false;
    }
    private ChatUser createChatUser(String sessionId) {
        User user = getUserFromSessionId(sessionId);
        return ChatUser.createChatUser(user);
    }

    private Room findRoomChatByRoomName(String roomName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomName").is(roomName));
        return mongoTemplate.findOne(query, Room.class);
    }


    private User getUserFromSessionId(String sessionId) {
        return userRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public void saveChatRoom(Room room) {
        roomRepository.save(room);
    }
}
