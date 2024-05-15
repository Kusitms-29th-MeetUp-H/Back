package com.kusitms29.backendH.api.community.service.dto.response;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentCreateResponseDto {
    private Long commentId;
    private String writerImage;
    private String writerName;
    private String createdDate;
    private String content;
    private boolean isCommentedByUser;

    public static CommentCreateResponseDto of(Long commentId, String writerImage, String writerName,
                                              LocalDateTime createdDate, String content, boolean isCommentedByUser) {

        return CommentCreateResponseDto.builder()
                .commentId(commentId)
                .writerImage(writerImage)
                .writerName(writerName)
                .createdDate(TimeCalculator.calculateTimeDifference(createdDate))
                .content(content)
                .isCommentedByUser(isCommentedByUser)
                .build();
    }
}
