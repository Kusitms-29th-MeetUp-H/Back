package com.kusitms29.backendH.api.user.service.dto.request.schoolEmail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SchoolEmailVerificationRequestDto {
    private String univName;
    private String email;
    private int code;
}
