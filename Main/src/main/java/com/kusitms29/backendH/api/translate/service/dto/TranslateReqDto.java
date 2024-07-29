package com.kusitms29.backendH.api.translate.service.dto;

public record TranslateReqDto(
        String language,
        String type,
        String message
) {
}
