package com.kusitms29.backendH.domain.sync.application.controller.dto.response;

import java.util.List;

public record SyncGraphResponseDto(
        List<GraphElement> data,
        String status

) {
    public static SyncGraphResponseDto of(List<GraphElement> data, String status){
        return new SyncGraphResponseDto(data, status);
    }
}
