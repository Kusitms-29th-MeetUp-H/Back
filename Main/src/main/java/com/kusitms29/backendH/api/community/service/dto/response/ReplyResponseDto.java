package com.kusitms29.backendH.api.community.service.dto.response;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReplyResponseDto {
    private Long replyId;
    private String writerImage;
    private String writerName;
    private String createdDate;
    private String content;
    private int likeCnt;
    private boolean isLikedByUser;
    private int reportedCnt;
    private boolean isRepliedByUser;

    public static ReplyResponseDto of(Long replyId, String writerImage, String writerName,
                                      LocalDateTime createdDate, String content,
                                      int likeCnt, boolean isLikedByUser, int reportedCnt, boolean isRepliedByUser) {
        return ReplyResponseDto.builder()
                .replyId(replyId)
                .writerImage(writerImage)
                .writerName(writerName)
                .createdDate(TimeCalculator.calculateTimeDifference(createdDate))
                .content(content)
                .likeCnt(likeCnt)
                .isLikedByUser(isLikedByUser)
                .reportedCnt(reportedCnt)
                .isRepliedByUser(isRepliedByUser)
                .build();
    }

}
