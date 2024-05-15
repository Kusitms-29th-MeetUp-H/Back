package com.kusitms29.backendH.domain.category.entity;

import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_TYPE;

@RequiredArgsConstructor
@Getter
public enum Type {
    //외국어 : 언어 교환, 튜터링, 스터디, 기타
    //문화/예술 : 문화/예술, 영화, 드라마, 미술/디자인, 공연/전시, 음악, 기타
    //여행/동행 : 관광지, 자연, 휴양, 기타
    //액티비티 : 러닝/산책, 등산, 클라이밍, 자전거, 축구, 서핑, 테니스, 볼링, 탁구, 기타
    //푸드드링크 : 맛집, 카페, 술, 기타
    //기타
    LANGUAGE("외국어"),
    ENTERTAINMENT("문화/예술"),
    TRAVEL("여행/동행"),
    ACTIVITY("액티비티"),
    FOOD("푸드드링크"),
    ETC("기타");

    private final String stringType;
    public static Type getEnumTypeFromStringType(String stringType) {
        if (stringType == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(type -> type.stringType.equals(stringType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_TYPE));
    }
}
