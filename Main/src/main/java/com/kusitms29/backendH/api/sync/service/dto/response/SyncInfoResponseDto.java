package com.kusitms29.backendH.api.sync.service.dto.response;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.SyncType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record SyncInfoResponseDto(
        Long syncId,
        String syncType,
        String type,
        String image,
        int userCnt,
        int totalCnt,
        String syncName,
        String location,
        String date
) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M/d일 (EE) aa h시");

    public static SyncInfoResponseDto of(Long syncId,
                                         SyncType syncType,
                                         Type type,
                                         String image,
                                         int userCnt,
                                         int totalCnt,
                                         String syncName,
                                         String location,
                                         LocalDateTime date){
        String formattedDate = date.format(DATE_TIME_FORMATTER);
        return new SyncInfoResponseDto(syncId, String.valueOf(syncType), String.valueOf(type), image, userCnt, totalCnt, syncName, location, formattedDate);
    }
}