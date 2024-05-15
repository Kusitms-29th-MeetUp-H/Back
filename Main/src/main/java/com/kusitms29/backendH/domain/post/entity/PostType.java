package com.kusitms29.backendH.domain.post.entity;

import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_POST_TYPE;

@RequiredArgsConstructor
@Getter
public enum PostType {
    C("생활"),
    Q("질문");
    private final String stringPostType;
    public static PostType getEnumPostTypeFromStringPostType(String stringPostType) {
        return Arrays.stream(values())
                .filter(postType -> postType.stringPostType.equals(stringPostType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_POST_TYPE));
    }
}
