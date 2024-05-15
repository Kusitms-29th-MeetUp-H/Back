package com.kusitms29.backendH.api.community.service.dto.response;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class PostDetailResponseDto {
    private String postType;
    private String writerImage;
    private String writerName;
    private String createdDate;
    private String title;
    private String content;
    private int likeCnt;
    private boolean isLikedByUser;
    private int commentCnt;
    private boolean isPostedByUser;
    private List<String> imageUrls;

    public static PostDetailResponseDto of(String postType, String writerImage, String writerName,
                                           LocalDateTime createdDate, String title, String content,
                                           int likeCnt, boolean isLikedByUser, int commentCnt, boolean isPostedByUser,
                                           List<String> imageUrls) {
        return PostDetailResponseDto.builder()
                .postType(postType)
                .writerImage(writerImage)
                .writerName(writerName)
                .createdDate(TimeCalculator.calculateTimeDifference(createdDate))
                .title(title)
                .content(content)
                .likeCnt(likeCnt)
                .isLikedByUser(isLikedByUser)
                .commentCnt(commentCnt)
                .isPostedByUser(isPostedByUser)
                .imageUrls(imageUrls)
                .build();
    }


}
