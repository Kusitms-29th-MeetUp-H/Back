package com.kusitms29.backendH.domain.chat.service;

import com.kusitms29.backendH.domain.chat.entity.ChatContent;
import com.kusitms29.backendH.domain.chat.entity.ChatUser;
import com.kusitms29.backendH.domain.chat.entity.Room;
import com.kusitms29.backendH.domain.chat.repository.RoomRepository;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.service.SyncReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.infra.external.fcm.service.PushNotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomAppender {
    private final RoomRepository roomRepository;
    private final PushNotificationService pushNotificationService;
    private final SyncReader syncReader;
    @Transactional
    public void createRoom(List<User> userList, Boolean isPossible, Long syncId) {
        Room room = null;
        if (isPossible) {
            room = roomRepository.save(
                    Room.createRoom(userList.stream().map(
                                            user -> ChatUser.createChatUser(user))
                                    .toList(),
                            generateRandomUuid(syncId)
                    )
            );

            // 채팅방 개설 알림
            pushNotificationService.sendChatRoomNotice(userList, syncId, room.getRoomSession());

            // 채팅 내용 추가
            Sync sync = syncReader.findById(syncId);

            for (User user : userList) {
                if (sync.getUser().getId().equals(user.getId())) {
                    ChatContent chatContent = ChatContent.createChatContent(user.getUserName(), "환영합니다", room);
                    room.addChatContent(chatContent);
                }
            }
        } else {
            List<ChatUser> chatUsers = userList.stream()
                    .map(ChatUser::createChatUser)
                    .toList();

            // 기존 채팅방에 사용자 추가
            for (ChatUser chatUser : chatUsers) {
                if (!room.getChatUserList().contains(chatUser)) {
                    room.addChatRoom(chatUser);
                }
            }
            roomRepository.save(room);
        }
    }
    private String generateRandomUuid(Long syncId) {
        UUID randomUuid = UUID.randomUUID();
        String uuidAsString = randomUuid.toString().replace("-", "");
        return syncId + "_" + uuidAsString.substring(0, 6);
    }
}
