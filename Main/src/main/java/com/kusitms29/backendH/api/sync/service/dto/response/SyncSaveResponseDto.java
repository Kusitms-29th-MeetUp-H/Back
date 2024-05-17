package com.kusitms29.backendH.api.sync.service.dto.response;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
public class SyncSaveResponseDto {
    private long syncId;
    private String userIntro;
    private String syncIntro;
    private String syncType;
    private String syncName;
    private String image;
    private String location;
    private LocalDateTime date; //일회성, 내친소

    private String regularDay; //지속성 요일
    private LocalTime regularTime; //지속성 시간
    private LocalDateTime routineDate; //지속성 첫모임

    private int member_min;

    private int member_max;

    private String type; //외국어, 엔터테인먼트, ...

    private String detailType;


    public static SyncSaveResponseDto of(Long syncId, String userIntro, String syncIntro,
                                         SyncType syncType, String syncName,
                                         String image, String location,
                                         LocalDateTime date,
                                         String regularDay, LocalTime regularTime, LocalDateTime routineDate,
                                         int member_min, int member_max,
                                         Type type, String detailType) {
        return SyncSaveResponseDto.builder()
                .syncId(syncId)
                .userIntro(userIntro)
                .syncIntro(syncIntro)
                .syncType(syncType.getStringSyncType())
                .syncName(syncName)
                .image(image)
                .location(location)
                .date(date)
                .regularDay(regularDay)
                .regularTime(regularTime)
                .routineDate(routineDate)
                .member_min(member_min)
                .member_max(member_max)
                .type(type.getStringType())
                .detailType(detailType)
                .build();
    }
}
