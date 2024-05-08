package com.kusitms29.backendH.domain.user.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CountryResponseDto {
    private Integer currentCount;
    private List<CountryDataDto> data;
    private Integer matchCount;
    private Integer page;
    private Integer perPage;
    private Integer totalCount;
}
