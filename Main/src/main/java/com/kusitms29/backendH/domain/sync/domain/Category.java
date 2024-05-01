package com.kusitms29.backendH.domain.sync.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {

    //일회성, 지속성, 내친소
    ONETIME("oneTime"),
    LONGTIME("longTime"),
    FROM_FRIEND("fromFriend");

    private final String category;
}
