package com.kusitms29.backendH.domain.user.application.controller.dto.request.schoolEmail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SchoolEmailRequestDto {
    private String email;
    private String univName;
}
