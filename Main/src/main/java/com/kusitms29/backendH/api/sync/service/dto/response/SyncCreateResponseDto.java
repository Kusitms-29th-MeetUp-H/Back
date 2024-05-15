package com.kusitms29.backendH.api.sync.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SyncCreateResponseDto {
    private Long syncId;
    private String link;
    private String syncType;
    private String parentCategory;
    private String childCategory;
    private String name;
    private String image;
    private String comment;
    private String location;
    private String date;
    private int meeting_cnt;
    private int member_min;
    private int member_max;

    public static SyncCreateResponseDto of(Long syncId, String link, String syncType,
                                           String parentCategory, String childCategory,
                                           String name, String image, String comment,
                                           String location, String date, int meeting_cnt,
                                           int member_min, int member_max) {
        return SyncCreateResponseDto.builder()
                .syncId(syncId)
                .link(link)
                .syncType(syncType)
                .parentCategory(parentCategory)
                .childCategory(childCategory)
                .name(name)
                .image(image)
                .comment(comment)
                .location(location)
                .date(date)
                .meeting_cnt(meeting_cnt)
                .member_min(member_min)
                .member_max(member_max)
                .build();
    }

}
