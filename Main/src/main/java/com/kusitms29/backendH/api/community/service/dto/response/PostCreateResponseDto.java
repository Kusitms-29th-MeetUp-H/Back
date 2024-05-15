package com.kusitms29.backendH.api.community.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostCreateResponseDto {
    private String postType;
    private String title;
    private String content;
    private List<String> imageUrls;

    public static PostCreateResponseDto of(String postType, String title,
                                           String content, List<String> imageUrls) {
        return PostCreateResponseDto.builder()
                .postType(postType)
                .title(title)
                .content(content)
                .imageUrls(imageUrls)
                .build();
    }
}
