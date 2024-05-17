package com.kusitms29.backendH.api.community.service.dto.response;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CommentResponseDto {
    private Long commentId;
    private String writerImage;
    private String writerName;
    private String createdDate;
    private String content;
    private int likeCnt;
    private boolean isLikedByUser;
    private int reportedCnt;
    private boolean isCommentedByUser;
    private List<ReplyCreateResponseDto> replyList;

    public static CommentResponseDto of(Long commentId, String writerImage, String writerName,
                                        LocalDateTime createdDate, String content, int likeCnt,
                                        boolean isLikedByUser, int reportedCnt, Boolean isCommentedByUser,
                                        List<ReplyCreateResponseDto> replyList) {

        return CommentResponseDto.builder()
                .commentId(commentId)
                .writerImage(writerImage)
                .writerName(writerName)
                .createdDate(TimeCalculator.calculateTimeDifference(createdDate))
                .content(content)
                .likeCnt(likeCnt)
                .isLikedByUser(isLikedByUser)
                .reportedCnt(reportedCnt)
                .isCommentedByUser(isCommentedByUser)
                .replyList(replyList)
                .build();
    }
}
