package com.kusitms29.backendH.domain.sync.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Language {
    KOREAN("korean"),
    ENGLISH("english");

    private final String language;
}
