package Backend.socket.domain.chat.application.service;

import Backend.socket.domain.chat.application.controller.dto.request.ChatListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageListRequestDto;
import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageRequestDto;

import Backend.socket.domain.chat.application.controller.dto.request.ChatMessageRoomRequestDto;
import Backend.socket.domain.chat.application.controller.dto.response.*;
import Backend.socket.domain.chat.domain.*;
import Backend.socket.domain.chat.repository.ChatRepository;
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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static Backend.socket.domain.chat.domain.ChatContent.createChatContent;
import static Backend.socket.global.error.ErrorCode.USER_NOT_FOUND;


@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {
    private final MongoTemplate mongoTemplate;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ChatMessageResponseDto createSendMessageContent(String sessionId, ChatMessageRequestDto chatMessageRequestDto) {
        Chat chat = getChatBySessions(sessionId, chatMessageRequestDto.getChatSession());
        ChatContent chatContent = createChatContent(chatMessageRequestDto.getFromUserName(), chatMessageRequestDto.getContent(), chat);
        ChatMessageElementResponseDto chatMessage = ChatMessageElementResponseDto.of(chatContent);
        List<String> sessionIdList = getSessionIdList(sessionId, chatMessageRequestDto.getChatSession());
        saveChat(chat);
        return ChatMessageResponseDto.of(chatMessageRequestDto.getToUserName(), sessionIdList, chatMessage);
    }
    public ChatMessageRoomResponseDto createSendMessageContentInRoom(String roomName, ChatMessageRoomRequestDto chatMessageRoomRequestDto) {
        Room room = getChatBySessionsInRoom(roomName, chatMessageRoomRequestDto.getChatSession());
        ChatContent chatContent = createChatContent(chatMessageRoomRequestDto.getFromUserName(), chatMessageRoomRequestDto.getContent(), room);
        ChatMessageElementResponseDto chatMessage = ChatMessageElementResponseDto.of(chatContent);
        List<String> sessionIdList = getSessionIdListInRoom(roomName, chatMessageRoomRequestDto.getChatSession());
        saveChatRoom(room);
        return ChatMessageRoomResponseDto.of(chatMessageRoomRequestDto.getToRoomName(), sessionIdList, chatMessage);
    }

    public ChatMessageListResponseDto sendChatDetailMessage(String sessionId, ChatMessageListRequestDto chatMessageListRequestDto) {
        Chat chat = getChatBySessions(sessionId, chatMessageListRequestDto.getChatSession());
        ChatUserResponseDto chatUserResponseDto = getChatUserResponseDto(chat, chatMessageListRequestDto.getFromUserName());
        List<ChatMessageElementResponseDto> chatMessageList = ChatMessageElementResponseDto.listOf(chat.getChatContentList());
        saveChat(chat);
        return ChatMessageListResponseDto.of(chatUserResponseDto, chatMessageList);
    }

    public ChatListResponseDto sendUserChatListMessage(String sessionId, ChatListRequestDto chatListRequestDto) {
        List<Chat> chatList = findChatListBySession(sessionId);
        List<UserChatResponseDto> userChatResponseDtoList = createUserChatResponseDto(chatList, chatListRequestDto.getUserName());
        userChatResponseDtoList.sort(Comparator.comparing(UserChatResponseDto::getTime).reversed());
        return ChatListResponseDto.of(userChatResponseDtoList);
    }

    private List<String> getSessionIdList(String firstSessionId, String secondSessionId) {
        List<String> sessionList = new ArrayList<>();
        sessionList.add(firstSessionId);
        sessionList.add(secondSessionId);
        return sessionList;
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

    private ChatUserResponseDto getChatUserResponseDto(Chat chat, String name) {
        ChatUser chatUser = getChatUserReceivedUser(chat, name);
        return ChatUserResponseDto.of(chatUser);
    }

    private List<UserChatResponseDto> createUserChatResponseDto(List<Chat> chatList, String userName) {
        List<Chat> filterChat = getChatEmptyContentFilter(chatList);
        return filterChat.stream()
                .map(chat ->
                        UserChatResponseDto.of(
                                getChatUserReceivedUser(chat, userName),
                                getLastChatContent(chat.getChatContentList()).getContent(),
                                getLastChatContent(chat.getChatContentList()).getTime()))
                .collect(Collectors.toList());
    }

    private List<Chat> getChatEmptyContentFilter(List<Chat> chatList) {
        return chatList.stream()
                .filter(chat -> (chat.getChatContentList().size() != 0))
                .collect(Collectors.toList());
    }

    private ChatUser getChatUserReceivedUser(Chat chat, String name) {
        if (!Objects.equals(chat.getChatUserList().get(0).getName(), name))
            return chat.getChatUserList().get(0);
        else
            return chat.getChatUserList().get(1);
    }


    private ChatContent getLastChatContent(List<ChatContent> chatContentList) {
        return chatContentList.get(chatContentList.size() - 1);
    }

    private Chat getChatBySessions(String firstSessionId, String secondSessionId) {
        Chat chat = findFirstChatBySessions(firstSessionId, secondSessionId);
        if (Objects.isNull(chat)) {
            ChatUser firstChatUser = createChatUser(firstSessionId);
            ChatUser secondChatUser = createChatUser(secondSessionId);
            return Chat.creatChat(firstChatUser, secondChatUser);
        } else
            return chat;
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

    private String getReceivedUserName(Chat chat, String user) {
        if (!Objects.equals(chat.getChatUserList().get(0).getName(), user))
            return chat.getChatUserList().get(0).getName();
        else
            return chat.getChatUserList().get(1).getName();
    }

    private Chat findFirstChatBySessions(String firstSessionId, String secondSessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatUserList.sessionId").all(firstSessionId, secondSessionId));
        return mongoTemplate.findOne(query, Chat.class);
    }
    private Room findRoomChatByRoomName(String roomName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomName").is(roomName));
        return mongoTemplate.findOne(query, Room.class);
    }

    private List<Chat> findChatListBySession(String sessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatUserList.sessionId").all(sessionId));
        return mongoTemplate.find(query, Chat.class);
    }

    private User getUserFromSessionId(String sessionId) {
        return userRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }
    public void saveChatRoom(Room room) {
        roomRepository.save(room);
    }
}
