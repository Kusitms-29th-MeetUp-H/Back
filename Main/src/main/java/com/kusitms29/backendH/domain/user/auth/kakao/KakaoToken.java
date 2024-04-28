package com.kusitms29.backendH.domain.user.auth.kakao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KakaoToken {
    private static final String TOKEN_TYPE = "Bearer ";
    private String accessToken;

    public static KakaoToken createKakaoToken(String authToken) {
        return new KakaoToken(authToken);
    }

    public String getAccessTokenWithTokenType() {
        return TOKEN_TYPE + accessToken;
    }
}
