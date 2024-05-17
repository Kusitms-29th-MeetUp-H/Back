package com.kusitms29.backendH.api.sync.service.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SyncCreateRequestDto {
    private String userIntro;
    private String syncIntro;
    private String syncType;
    private String syncName;
    private String location;
    private String date; //일회성, 내친소

    private String regularDay; //지속성
    private String regularTime; //지속성
    private String routineDate; //지속성

    private int member_min;

    private int member_max;

    private String type; //언어교환, 엔터테인먼트, ...

    private String detailType;
}
