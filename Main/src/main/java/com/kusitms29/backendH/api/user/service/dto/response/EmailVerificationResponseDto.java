package com.kusitms29.backendH.api.user.service.dto.response;

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
