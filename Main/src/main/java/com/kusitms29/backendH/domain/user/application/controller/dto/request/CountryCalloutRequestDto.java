package com.kusitms29.backendH.domain.user.application.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CountryCalloutRequestDto {
    private Integer page;
    private Integer perPage;
    private String language;
}

