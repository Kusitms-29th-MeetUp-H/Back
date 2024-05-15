package com.kusitms29.backendH.domain.sync.entity;

import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_GENDER_TYPE;

@RequiredArgsConstructor
@Getter
public enum Gender {

    MAN("남성"),
    WOMAN("여성"),
    SECRET("비공개");

    private final String stringGender;

    public static Gender getEnumFROMStringGender(String stringGender) {
        return Arrays.stream(values())
                .filter(gender -> gender.stringGender.equals(stringGender))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_GENDER_TYPE));
    }

}
