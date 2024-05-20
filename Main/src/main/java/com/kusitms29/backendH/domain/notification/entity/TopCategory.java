package com.kusitms29.backendH.domain.notification.entity;

import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_NOTIFICATION_TOP_CATEGORY;

@RequiredArgsConstructor
@Getter
public enum TopCategory {
    ACTIVITY("활동"),
    MY_SYNC("내싱크");

    private final String stringTopCategory;

    public static TopCategory getEnumTopCategoryFromStringTopCategory(String strTopCategory) {
        return Arrays.stream(values())
                .filter(topCategory -> topCategory.stringTopCategory.equals(strTopCategory))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_NOTIFICATION_TOP_CATEGORY));
    }
}
