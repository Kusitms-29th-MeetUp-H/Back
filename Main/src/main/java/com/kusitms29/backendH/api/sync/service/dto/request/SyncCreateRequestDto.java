package com.kusitms29.backendH.api.sync.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SyncCreateRequestDto {
    private String syncType;
    private String parentCategory;
    private String childCategory;
    private String name;
    private String comment;
    private String location;
    private String date;
    private int meeting_cnt;
    private int member_min;
    private int member_max;
}
