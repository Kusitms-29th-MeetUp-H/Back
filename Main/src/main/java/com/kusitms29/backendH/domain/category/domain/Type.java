package com.kusitms29.backendH.domain.category.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Type {
    //언어 : 언어 교환, 튜터링, 스터디
    //엔터테인먼트/예술 : 문화 예술, 영화, 드라마, 미술/디자인, 공연/전시, 음악
    //여행/동행 : 관광지, 자연, 휴양
    //액티비티 : 러닝/산책, 등산, 클라이밍, 자전거, 축구, 서핑, 테니스, 볼링, 탁구
    //푸드드링크 : 맛집, 카페, 술
    LANGUAGE("언어"),
    ENTERTAINMENT("엔터테인먼트/예술"),
    TRAVEL("여행/동행"),
    ACTIVITY("액티비티"),
    FOOD("푸드드링크");

    private final String type;
}
