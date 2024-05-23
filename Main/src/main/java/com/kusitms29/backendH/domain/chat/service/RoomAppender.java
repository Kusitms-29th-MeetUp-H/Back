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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomAppender {
    private final RoomRepository roomRepository;
    private final PushNotificationService pushNotificationService;
    private final SyncReader syncReader;
    private final RoomReader roomReader;
    @Transactional
    public void createRoom(List<User> userList, Boolean isPossible, Long syncId, User owner,User joinUser) {
        Room room = null;
        Sync sync = syncReader.findById(syncId);
        if (isPossible) {
            // 채팅 내용 추가

            List<ChatContent> contents = new ArrayList<>();


            room = roomRepository.save(
                    Room.createRoom(userList.stream().map(
                                            user -> ChatUser.createChatUser(user))
                                    .toList(),
                            contents,
                            generateRandomUuid(syncId),
                            sync,
                            owner
                    )
            );
            for (User user : userList) {
                if (sync.getUser().getId().equals(user.getId())) {
                    ChatContent chatContent = ChatContent.createChatContent(user.getUserName(), "모두 반가워요~", room,"https://sync-content-bucket-01.s3.ap-northeast-2.amazonaws.com/a2d3a182-9054-42b9-b439-616bcbba9e87.png");
                    contents.add(chatContent);
                }
            }
            roomRepository.save(room);
        } else {
            room = roomReader.getRoomBySyncName(sync.getSyncName());
            room.addChatRoom(ChatUser.createChatUser(joinUser));
            roomRepository.save(room);
        }
        // 채팅방 개설 알림
        pushNotificationService.sendChatRoomNotice(userList, syncId, room.getRoomSession());
    }
    private String generateRandomUuid(Long syncId) {
        UUID randomUuid = UUID.randomUUID();
        String uuidAsString = randomUuid.toString().replace("-", "");
        return syncId + "_" + uuidAsString.substring(0, 6);
    }
}
