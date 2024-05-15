package com.kusitms29.backendH.api.user.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CountryDataDto {
    private String ISO_alpha2;
    private String ISO_alpha3;
    private Integer ISO_numeric;
    private String 대륙명_공통_대륙코드;
    private String 대륙명_행정표준코드;
    private String 대륙명_외교부_직제;
    private String 영문명;
    private String 한글명;
}
