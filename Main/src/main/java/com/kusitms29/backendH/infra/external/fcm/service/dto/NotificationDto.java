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
    private String id; //알림 받는 이
    private String str1; //알림 내역1
    private String str2; //알림 내역2
    /**
     * 커뮤니티 : 글이름 + 댓글단이
     * 채팅 : 채팅내용
     * 공지 : 싱크이름 (아예 없어도?)
     * 일정 : 유저이름 + 싱크이름
     * 후기 : 유저이름
     */

    //글 Id, 채팅 Id, 싱크 Id, 싱크 Id, ..  기타 정보도 넘겨야 하나
    private MessageTemplate template;
}
