package com.kusitms29.backendH.api.notification.service.dto;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class NotificationHistoryResponseDto {
    private String infoId; //커뮤니티: 게시글, 일정: 싱크, 리뷰: 싱크, 채팅: 채팅방, 채팅개설공지: 싱크
    private String title;
    private String content;
    private String detailContent;
    private String createdDate;

    public static NotificationHistoryResponseDto of(String infoId, String title, String content,
                                                    String detailContent, LocalDateTime createdDate) {

        return NotificationHistoryResponseDto.builder()
                .infoId(infoId)
                .title(title)
                .content(content)
                .detailContent(detailContent)
                .createdDate(TimeCalculator.calculateTimeDifference(createdDate))
                .build();
    }

}
