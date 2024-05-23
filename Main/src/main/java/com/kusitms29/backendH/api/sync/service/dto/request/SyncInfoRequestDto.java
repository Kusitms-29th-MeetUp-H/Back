package com.kusitms29.backendH.api.sync.service.dto.request;

public record SyncInfoRequestDto(
        int take,
        String syncType,
        String type,
        String language
) {
    public SyncInfoRequestDto {
        if (take < 0) {
            take = 0;
        }
        if (language == null || language.isBlank()) {
            language = "한국어";
        }
    }
}