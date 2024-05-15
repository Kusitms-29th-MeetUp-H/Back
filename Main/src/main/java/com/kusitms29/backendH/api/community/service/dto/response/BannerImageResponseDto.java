package com.kusitms29.backendH.api.community.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BannerImageResponseDto {
    String image;

    public static BannerImageResponseDto of(String image) {
        return BannerImageResponseDto.builder()
                .image(image)
                .build();
    }
}
