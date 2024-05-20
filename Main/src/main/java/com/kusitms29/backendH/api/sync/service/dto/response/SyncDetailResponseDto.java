package com.kusitms29.backendH.api.sync.service.dto.response;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.SyncType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record SyncDetailResponseDto(
        String syncName,
        String syncImage,
        String syncType,
        String type,
        String syncIntro,
        String regularDate,
        String date,
        String location,
        int userCnt,
        int totalCnt,
        String userImage,
        String userName,
        String university,
        String userIntro,
        Boolean isFull
) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M월 d일 (EE) a h:mm");
    public static SyncDetailResponseDto oneTimeOf(String syncName,
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
                                           String userIntro,
                                                  Boolean isFull){
        String formattedDate = date.format(DATE_TIME_FORMATTER);
        return new SyncDetailResponseDto(syncName, syncImage, String.valueOf(syncType), String.valueOf(type), syncIntro, null, formattedDate, location, userCnt, totalCnt, userImage, userName, university, userIntro, isFull);
    }
    public static SyncDetailResponseDto longTimeOf(String syncName,
                                                  String syncImage,
                                                  SyncType syncType,
                                                  Type type,
                                                  String syncIntro,
                                                  String regularDay,
                                                  LocalTime regularTime,
                                                  LocalDateTime date,
                                                  String location,
                                                  int userCnt,
                                                  int totalCnt,
                                                  String userImage,
                                                  String userName,
                                                  String university,
                                                  String userIntro,
                                                   Boolean isFull){
        String formattedDate = date.format(DATE_TIME_FORMATTER);
        String formattedRegularTime = regularTime.format(DateTimeFormatter.ofPattern("a h:mm"));
        String regularDate = "매주 " + regularDay + " " + formattedRegularTime;
        return new SyncDetailResponseDto(syncName, syncImage, String.valueOf(syncType), String.valueOf(type), syncIntro, regularDate, formattedDate, location, userCnt, totalCnt, userImage, userName, university, userIntro, isFull);
    }
}
