package com.kusitms29.backendH.api.user.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserSignInRequestDto {
    private String platform;
}
