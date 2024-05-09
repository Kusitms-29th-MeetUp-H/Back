package com.kusitms29.backendH.domain.sync.application.controller.dto.request;

import java.util.List;

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
