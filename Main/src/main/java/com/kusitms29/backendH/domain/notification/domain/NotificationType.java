package com.kusitms29.backendH.domain.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationType {
    SYNC_REMIND("sync_remind"),
    CHAT("chat"),
    COMMENT("comment");

    private final String type;
}
