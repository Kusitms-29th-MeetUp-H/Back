package com.kusitms29.backendH.domain.sync.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {

    MAN("man"),
    WOMAN("woman"),
    SECRET("secret");

    private final String gender;
}
