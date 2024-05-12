package com.kusitms29.backendH.domain.post.application.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostCalculateDto {
    private int likeCount;
    private boolean isLikedByUser;
    private int commentCount;
    private boolean isPostedByUser;
}
