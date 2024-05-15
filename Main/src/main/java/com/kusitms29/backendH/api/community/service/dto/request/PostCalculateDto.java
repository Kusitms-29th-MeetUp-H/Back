package com.kusitms29.backendH.api.community.service.dto.request;

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
