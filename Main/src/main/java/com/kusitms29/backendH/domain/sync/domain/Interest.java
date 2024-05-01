package com.kusitms29.backendH.domain.sync.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Interest {

    //언어교환, 문화예술, 여행동행, 액티비티, 푸드드링크, 기타(6)
    LANGUAGE_EXCHANGE("languageExchange"),
    CULTURAL_ART("culturalArt"),
    TRAVEL_COMPANION("travelCompanion"),
    ACTIVITY("activity"),
    FOOD_AND_DRINK("foodAndDrink"),
    ETC("etc");

    private final String interest;
}
