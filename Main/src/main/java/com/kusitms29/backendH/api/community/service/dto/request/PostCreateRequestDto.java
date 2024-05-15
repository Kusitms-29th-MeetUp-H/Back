package com.kusitms29.backendH.api.community.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCreateRequestDto {
    private String postType;
    private String title;
    private String content;
}
