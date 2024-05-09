package com.kusitms29.backendH.domain.sync.application.controller.dto.response;

public record SyncDetailResponseDto(
        String syncName,
        String syncImage,
        String syncType,
        String type,
        String syncIntro,
        String date,
        String location,
        String userCnt,
        String totalCnt,
        String userImage,
        String userName,
        String university,
        String userIntro
) {
    public static SyncDetailResponseDto of(String syncName,
                                           String syncImage,
                                           String syncType,
                                           String type,
                                           String syncIntro,
                                           String date,
                                           String location,
                                           String userCnt,
                                           String totalCnt,
                                           String userImage,
                                           String userName,
                                           String university,
                                           String userIntro){
        return new SyncDetailResponseDto(syncName, syncImage, syncType, type, syncIntro, date, location, userCnt, totalCnt, userImage, userName, university, userIntro);
    }
}
