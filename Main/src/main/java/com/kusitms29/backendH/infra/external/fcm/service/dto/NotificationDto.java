package com.kusitms29.backendH.infra.external.fcm.service.dto;

import com.kusitms29.backendH.infra.external.fcm.MessageTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private String id;
    private String str1;
    private String str2;
    /**
     * 커뮤니티 : 글이름 + 댓글단이
     * 채팅 : 채팅내용
     * 공지 : 싱크이름 (아예 없어도?)
     * 일정 : 유저이름 + 싱크이름
     * 후기 : 유저이름
     */
    private MessageTemplate template;
}
