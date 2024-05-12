package com.kusitms29.backendH.domain.comment.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentCreateResponseDto {
    private Long commentId;
    private String writerImage;
    private String writerName;
    private LocalDateTime createdDate;
    private String content;
    private boolean isCommentedByUser;

    public static CommentCreateResponseDto of(Long commentId, String writerImage, String writerName,
                                              LocalDateTime createdDate, String content, boolean isCommentedByUser) {

        return CommentCreateResponseDto.builder()
                .commentId(commentId)
                .writerImage(writerImage)
                .writerName(writerName)
                .createdDate(createdDate)
                .content(content)
                .isCommentedByUser(isCommentedByUser)
                .build();
    }
}
