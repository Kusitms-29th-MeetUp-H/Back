package com.kusitms29.backendH.domain.sync.application.controller.dto.request;

import java.util.List;

public record SyncInfoRequest2Dto(
        int take,
        List<String> syncTypeFilter,
        List<String> typeFilter
) {
    public SyncInfoRequest2Dto {
        if (take < 0) {
            take = 0;
        }
    }
}
