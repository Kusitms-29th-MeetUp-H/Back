package com.kusitms29.backendH.api.community.service.dto.response;

import com.kusitms29.backendH.global.common.TimeCalculator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReplyCreateResponseDto {
    private Long replyId;
    private String writerImage;
    private String writerName;
    private String createdDate;
    private String content;
    private boolean isRepliedByUser;

    public static ReplyCreateResponseDto of(Long commentId, String writerImage, String writerName,
                                              LocalDateTime createdDate, String content, boolean isRepliedByUser) {

        return ReplyCreateResponseDto.builder()
                .replyId(commentId)
                .writerImage(writerImage)
                .writerName(writerName)
                .createdDate(TimeCalculator.calculateTimeDifference(createdDate))
                .content(content)
                .isRepliedByUser(isRepliedByUser)
                .build();
    }
}
