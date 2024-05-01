package com.kusitms29.backendH.domain.sync.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SyncStatus {

    RECRUITING("recruiting"),
    COMPLETED("recruitment_completed"),
    DELETED("meeting_deleted");

    private final String syncStatus;
}
