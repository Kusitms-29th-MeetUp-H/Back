package com.kusitms29.backendH.api.user.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OnBoardingResponseDto {
    private String language;
    private String profileImage;
    private String userName;
    private String countryName;
    private String gender;
    private String university;
    private String email;
    private String syncType;
    private List<String> detailTypes;

    public static OnBoardingResponseDto of(String language,  String profileImage, String userName, String countryName, String gender,
                                           String university, String email, String syncType, List<String> detailTypes) {
        return OnBoardingResponseDto.builder()
                .language(language)
                .profileImage(profileImage)
                .userName(userName)
                .countryName(countryName)
                .gender(gender)
                .university(university)
                .email(email)
                .syncType(syncType)
                .detailTypes(detailTypes)
                .build();
    }
}
