package com.kusitms29.backendH.domain.participation.application.controller.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EnterSyncResponseDto {
    private String userId;
    private String syncId;
    public static EnterSyncResponseDto of(Long userId, Long syncId) {
        return EnterSyncResponseDto.builder()
                .userId(String.valueOf(userId))
                .syncId(String.valueOf(syncId))
                .build();
    }
}
