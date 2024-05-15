package com.kusitms29.backendH.domain.sync.entity;

import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_LANGUAGE_TYPE;

@RequiredArgsConstructor
@Getter
public enum Language {
    KOREAN("korean"),
    ENGLISH("english");

    private final String stringLanguage;

    public static Language getEnumLanguageFromStringLanguage(String stringLanguage) {
        return Arrays.stream(values())
                .filter(language -> language.stringLanguage.equals(stringLanguage))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_LANGUAGE_TYPE));
    }

}
