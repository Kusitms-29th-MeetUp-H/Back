package com.kusitms29.backendH.infra.config.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TokenInfo {
    private String accessToken;
    private String refreshToken;

    public static TokenInfo of(String accessToken, String refreshToken) {
        return new TokenInfo(accessToken, refreshToken);
    }
}

