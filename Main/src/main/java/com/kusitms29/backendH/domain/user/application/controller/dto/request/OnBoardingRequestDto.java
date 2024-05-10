package com.kusitms29.backendH.domain.user.application.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OnBoardingRequestDto {
    private String language;
    private String userName;
    private String countryName;
    private String gender;
    private String university;
    private String email;
    private String syncType;
    private CategoryRequestDto categoryTypes;
}
