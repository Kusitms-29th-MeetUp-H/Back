package com.kusitms29.backendH.domain.chat.service;

import com.kusitms29.backendH.domain.chat.entity.ChatUser;
import com.kusitms29.backendH.domain.chat.entity.Room;
import com.kusitms29.backendH.domain.chat.repository.RoomRepository;
import com.kusitms29.backendH.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomAppender {
    private final RoomRepository roomRepository;
    @Transactional
    public void createRoom(List<User> userList, Boolean isPossible,Long syncId){
        if (isPossible)
            roomRepository.save(
                    Room.createRoom(userList.stream().map(
                            user -> ChatUser.createChatUser(user)   )
                            .toList(),
                            generateRandomUuid(syncId)
                    )
            );
    }
    private String generateRandomUuid(Long syncId) {
        UUID randomUuid = UUID.randomUUID();
        String uuidAsString = randomUuid.toString().replace("-", "");
        return syncId + "_" + uuidAsString.substring(0, 6);
    }
}
