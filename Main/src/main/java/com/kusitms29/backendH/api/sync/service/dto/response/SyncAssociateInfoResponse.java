package com.kusitms29.backendH.api.sync.service.dto.response;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.Locale;

@Getter
@Builder
public class SyncAssociateInfoResponse {
    private Long syncId;
    private String syncType;
    private String type;
    private String image;
    private int userCnt;
    private int totalCnt;
    private String syncName;
    private String location;
    private String date;
    private String associate;
    private Boolean isMarked;
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("M/d(EEE) a h시")
                    .withLocale(Locale.KOREAN)
                    .withDecimalStyle(DecimalStyle.of(Locale.KOREAN));

    public SyncAssociateInfoResponse(){

    }
    public SyncAssociateInfoResponse(
            Long syncId,
            String syncType,
            String type,
            String image,
            int userCnt,
            int totalCnt,
            String syncName,
            String location,
            String date,
            String associate,
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
        this.associate = associate;
        this.isMarked = isMarked;
    }
    public static SyncAssociateInfoResponse of(
            Long syncId,
            SyncType syncType,
            Type type,
            String image,
            int userCnt,
            int totalCnt,
            String syncName,
            String location,
            LocalDateTime date,
            String associate,
            Boolean isMarked) {
        String dateString = date != null ? date.format(DATE_TIME_FORMATTER) : null;
        return new SyncAssociateInfoResponse(
                syncId,
                syncType.getStringSyncType(),
                type.getStringType(),
                image,
                userCnt,
                totalCnt,
                syncName,
                location,
                dateString,
                associate,
                isMarked
        );
    }
}
