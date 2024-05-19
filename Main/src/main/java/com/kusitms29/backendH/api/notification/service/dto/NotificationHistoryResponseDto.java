package com.kusitms29.backendH.api.notification.service.dto;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class NotificationHistoryResponseDto {
    private String title;
    private String content;
    private String detailContent;
    private String createdDate;

    public static NotificationHistoryResponseDto of(String title, String content,
                                                    String detailContent, LocalDateTime createdDate) {

        return NotificationHistoryResponseDto.builder()
                .title(title)
                .content(content)
                .detailContent(detailContent)
                .createdDate(TimeCalculator.calculateTimeDifference(createdDate))
                .build();
    }

}
