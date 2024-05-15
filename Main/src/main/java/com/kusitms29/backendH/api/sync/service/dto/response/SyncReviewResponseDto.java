package com.kusitms29.backendH.api.sync.service.dto.response;

import java.time.Duration;
import java.time.LocalDateTime;

public record SyncReviewResponseDto(
        String image,
        String name,
        String university,
        String content,
        String date
) {
    public static SyncReviewResponseDto of(String image, String name, String university, String content, LocalDateTime date){
        return new SyncReviewResponseDto(image, name, university, content, calculateTimeDifference(date));
    }
    public static String calculateTimeDifference(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(date, now);
        long months = duration.toDays() / 30;
        if (months > 0) {
            return months + "달 전";
        }
        return "방금 전";
    }
}
