package com.kusitms29.backendH.domain.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PostType {
    C("생활"),
    Q("질문");
    private final String postType;
}
