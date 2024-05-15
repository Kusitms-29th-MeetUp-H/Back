package com.kusitms29.backendH.domain.sync.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SyncStatus {

    RECRUITING("모집중"),
    COMPLETED("모집완료"),
    DELETED("삭제된모임");

    private final String syncStatus;
}
