package com.kusitms29.backendH.domain.user.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailVerificationResponseDto {

    private boolean authResult;

    public static EmailVerificationResponseDto of(Boolean authResult) {
        return EmailVerificationResponseDto.builder()
                .authResult(authResult)
                .build();
    }
}
