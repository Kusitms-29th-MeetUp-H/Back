package com.kusitms29.backendH.api.sync.service.dto.response;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import com.kusitms29.backendH.domain.user.entity.User;

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
        Boolean isFull,
        Boolean isJoin,
        Boolean isMarked,
        Boolean isOwner
) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M월 d일 (EEE) a h:mm");
    public static SyncDetailResponseDto oneTimeOf(Sync sync,
                                                  User user,
                                                  int userCnt,
                                                  Boolean isFull,
                                                  Boolean isJoin,
                                                  Boolean isMarked,
                                                  Boolean isOwner){
        String formattedDate = sync.getDate().format(DATE_TIME_FORMATTER);
        return new SyncDetailResponseDto(sync.getSyncName(), sync.getImage(), sync.getSyncType().getStringSyncType(), sync.getType().getStringType(), sync.getSyncIntro(), null, formattedDate, sync.getLocation(), userCnt, sync.getMember_max(), user.getProfile(), user.getUserName(), user.getUniversity(), sync.getUserIntro(), isFull, isJoin,isMarked,isOwner);
    }
    public static SyncDetailResponseDto longTimeOf(Sync sync,
                                                   User user,
                                                   int userCnt,
                                                   Boolean isFull,
                                                   Boolean isJoin,
                                                   Boolean isMarked,
                                                   Boolean isOwner){
        String formattedDate = null;
        if(sync.getDate() != null) {
            formattedDate = sync.getDate().format(DATE_TIME_FORMATTER);
        }
        String formattedRegularTime = sync.getRegularTime().format(DateTimeFormatter.ofPattern("a h:mm"));
        String regularDate = "매주 " + sync.getRegularDay() + " " + formattedRegularTime;
        return new SyncDetailResponseDto(sync.getSyncName(), sync.getImage(), sync.getSyncType().getStringSyncType(), sync.getType().getStringType(), sync.getSyncIntro(), regularDate, formattedDate, sync.getLocation(), userCnt, sync.getMember_max(), user.getProfile(), user.getUserName(), user.getUniversity(), sync.getUserIntro(), isFull, isJoin, isMarked, isOwner);
    }
}
