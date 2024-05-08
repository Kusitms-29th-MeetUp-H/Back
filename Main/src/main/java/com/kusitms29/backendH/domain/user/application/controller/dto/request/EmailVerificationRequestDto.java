package com.kusitms29.backendH.domain.user.application.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EmailVerificationRequestDto {
    private String email;
    private String code;
}
