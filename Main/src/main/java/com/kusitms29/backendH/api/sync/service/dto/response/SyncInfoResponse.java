package com.kusitms29.backendH.api.sync.service.dto.response;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.Locale;
@Getter
@Builder
public class SyncInfoResponse {
    private Long syncId;
    private String syncType;
    private String type;
    private String image;
    private int userCnt;
    private int totalCnt;
    private String syncName;
    private String location;
    private String date;
    private Boolean isMarked;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("M/d(EEE) a h시")
                    .withLocale(Locale.KOREAN)
                    .withDecimalStyle(DecimalStyle.of(Locale.KOREAN));

    public SyncInfoResponse(){

    }
    public SyncInfoResponse(
            Long syncId,
            String syncType,
            String type,
            String image,
            int userCnt,
            int totalCnt,
            String syncName,
            String location,
            String date,
            Boolean isMarked) {
        this.syncId = syncId;
        this.syncType = syncType;
        this.type = type;
        this.image = image;
        this.userCnt = userCnt;
        this.totalCnt = totalCnt;
        this.syncName = syncName;
        this.location = location;
        this.date = date;
        this.isMarked = isMarked;
    }

    public static SyncInfoResponse of(
            Sync sync,
            int userCnt,
            Boolean isMarked) {
        String dateString = sync.getDate() != null ? sync.getDate().format(DATE_TIME_FORMATTER) : null;
        return new SyncInfoResponse(
                sync.getId(),
                sync.getSyncType().getStringSyncType(),
                sync.getType().getStringType(),
                sync.getImage(),
                userCnt,
                sync.getMember_max(),
                sync.getSyncName(),
                sync.getLocation(),
                dateString,
                isMarked
        );
    }

}