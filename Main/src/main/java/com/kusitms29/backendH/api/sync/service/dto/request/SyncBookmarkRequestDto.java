package com.kusitms29.backendH.api.sync.service.dto.request;

public record SyncBookmarkRequestDto(
        Long syncId,
        Boolean isMarked
) {
}
