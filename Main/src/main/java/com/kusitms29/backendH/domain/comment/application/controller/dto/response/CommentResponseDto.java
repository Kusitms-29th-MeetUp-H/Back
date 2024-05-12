package com.kusitms29.backendH.domain.comment.application.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentResponseDto {
    private Long commentId;
    private String writerImage;
    private String writerName;
    private LocalDateTime createdDate;
    private String content;
    private int likeCnt;
    private int reportedCnt;
    private boolean commentedByUser;


    public static CommentResponseDto of(Long commentId, String writerImage, String writerName,
                                        LocalDateTime createdDate, String content,
                                        int likeCnt, int reportedCnt, Boolean commentedByUser) {
        return CommentResponseDto.builder()
                .commentId(commentId)
                .writerImage(writerImage)
                .writerName(writerName)
                .createdDate(createdDate)
                .content(content)
                .likeCnt(likeCnt)
                .reportedCnt(reportedCnt)
                .commentedByUser(commentedByUser)
                .build();
    }
}
