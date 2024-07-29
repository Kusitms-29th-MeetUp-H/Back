package com.kusitms29.backendH.api.translate.service.dto;

public record TranslateResDto(
        String message
) {
    public static TranslateResDto of(String message){
        return new TranslateResDto(message);
    }
}
