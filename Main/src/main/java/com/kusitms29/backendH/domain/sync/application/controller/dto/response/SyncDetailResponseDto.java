package com.kusitms29.backendH.domain.sync.application.controller.dto.response;

import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.domain.sync.domain.SyncType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record SyncDetailResponseDto(
        String syncName,
        String syncImage,
        String syncType,
        String type,
        String syncIntro,
        String date,
        String location,
        int userCnt,
        int totalCnt,
        String userImage,
        String userName,
        String university,
        String userIntro
) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M월 d일 (E) a h:mm");
    public static SyncDetailResponseDto of(String syncName,
                                           String syncImage,
                                           SyncType syncType,
                                           Type type,
                                           String syncIntro,
                                           LocalDateTime date,
                                           String location,
                                           int userCnt,
                                           int totalCnt,
                                           String userImage,
                                           String userName,
                                           String university,
                                           String userIntro){
        String formattedDate = date.format(DATE_TIME_FORMATTER);
        return new SyncDetailResponseDto(syncName, syncImage, String.valueOf(syncType), String.valueOf(type), syncIntro, formattedDate, location, userCnt, totalCnt, userImage, userName, university, userIntro);
    }
}
