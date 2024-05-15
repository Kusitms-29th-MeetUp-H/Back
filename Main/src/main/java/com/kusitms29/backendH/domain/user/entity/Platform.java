package com.kusitms29.backendH.domain.user.entity;

import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_PLATFORM_TYPE;


@RequiredArgsConstructor
@Getter
public enum Platform {
    GOOGLE("google"),
    KAKAO("kakao"),
    WITHDRAW("withdraw");

    private final String stringPlatform;

    public static Platform getEnumPlatformFromStringPlatform(String stringPlatform) {
        return Arrays.stream(values())
                .filter(platform -> platform.stringPlatform.equals(stringPlatform))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_PLATFORM_TYPE));
    }
}

