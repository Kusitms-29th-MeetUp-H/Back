package com.kusitms29.backendH.api.user.service.dto.response;

import com.kusitms29.backendH.domain.sync.entity.SyncReview;

public record CreateReviewResponse(
        Long syncId,
        Long userId
) {
    public static CreateReviewResponse of(SyncReview syncReview){
        return new CreateReviewResponse(syncReview.getSync().getId(), syncReview.getUser().getId());
    }
}
