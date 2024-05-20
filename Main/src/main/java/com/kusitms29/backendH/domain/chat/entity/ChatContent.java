package com.kusitms29.backendH.domain.chat.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChatContent {
    private String userName;
    private String content;
    private LocalDateTime time;

    public static ChatContent createChatContent(String userName, String content, Room room) {
        ChatContent chatContent = ChatContent.builder()
                .userName(userName)
                .content(content)
                .time(LocalDateTime.now())
                .build();
        room.addChatContent(chatContent);
        return chatContent;
    }
}
