package com.kusitms29.backendH.api.user.service.dto.request;

public record CreateReviewRequest(
        Long syncId,
        String content
) {
}
