package com.kusitms29.backendH.application.sync.service.dto.request;

public record SyncInfoRequestDto(
        int take,
        String syncType,
        String type
) {
    public SyncInfoRequestDto {
        if (take < 0) {
            take = 0;
        }
    }
}
