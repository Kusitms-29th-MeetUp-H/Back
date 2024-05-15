package com.kusitms29.backendH.api.community.service.dto.response;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostSearchResponseDto {
    private Long postId;
    private String postType;
    private String userImage;
    private String userName;
    private String title;
    private String content;
    private String createdAt;

    public static PostSearchResponseDto of(Long postId, String postType,
                                           String userImage, String userName,
                                           String title, String content,
                                           LocalDateTime createdAt) {
        return PostSearchResponseDto.builder()
                .postId(postId)
                .postType(postType)
                .userImage(userImage)
                .userName(userName)
                .title(title)
                .content(content)
                .createdAt(TimeCalculator.calculateTimeDifference(createdAt))
                .build();
    }
}
