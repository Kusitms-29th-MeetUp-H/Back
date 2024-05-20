package com.kusitms29.backendH.api.user.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> detailTypes;
}
