package com.kusitms29.backendH.domain.chat.entity;

import com.kusitms29.backendH.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatUser {
    private String sessionId;
    private String name;
    private String profile;

    public static ChatUser createChatUser(User user) {
        return ChatUser.builder()
                .sessionId(user.getSessionId())
                .name(user.getUserName())
                .profile(user.getProfile())
                .build();
    }
}
