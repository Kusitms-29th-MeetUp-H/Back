package com.kusitms29.backendH.api.community.service.dto.response;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Builder
@Getter
public class PostResponseDto {
    private long postId;
    private String postType;
    private String writerImage;
    private String writerName;
    private String createdDate;
    private String title;
    private String content;
    private String representativeImage;
    private int likeCnt;
    private boolean isLikedByUser;
    private int commentCnt;
    private boolean isPostedByUser;

    public static PostResponseDto of(Long postId, String postType, String writerImage, String writerName,
                                     LocalDateTime createdDate, String title, String content, String representativeImage,
                                     int likeCnt, boolean isLikedByUser, int commentCnt, boolean isPostedByUser) {
        return PostResponseDto.builder()
                .postId(postId)
                .postType(postType)
                .writerImage(writerImage)
                .writerName(writerName)
                .createdDate(TimeCalculator.calculateTimeDifference(createdDate))
                .title(title)
                .content(content)
                .representativeImage(representativeImage)
                .likeCnt(likeCnt)
                .isLikedByUser(isLikedByUser)
                .commentCnt(commentCnt)
                .isPostedByUser(isPostedByUser)
                .build();
    }
}
