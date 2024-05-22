package com.kusitms29.backendH.api.sync.service.dto.response;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.SyncType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.Locale;

public record SyncInfoResponseDto(
        Long syncId,
        String syncType,
        String type,
        String image,
        int userCnt,
        int totalCnt,
        String syncName,
        String location,
        String date,
        Boolean isMarked
) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("M/d(EEE) a h시")
                    .withLocale(Locale.KOREAN)
                    .withDecimalStyle(DecimalStyle.of(Locale.KOREAN));
    public static SyncInfoResponseDto of(Long syncId,
                                         SyncType syncType,
                                         Type type,
                                         String image,
                                         int userCnt,
                                         int totalCnt,
                                         String syncName,
                                         String location,
                                         LocalDateTime date,
                                         Boolean isMarked){
        String formattedDate = date.format(DATE_TIME_FORMATTER);
        return new SyncInfoResponseDto(syncId, syncType.getStringSyncType(), type.getStringType(), image, userCnt, totalCnt, syncName, location, formattedDate, isMarked);
    }
}