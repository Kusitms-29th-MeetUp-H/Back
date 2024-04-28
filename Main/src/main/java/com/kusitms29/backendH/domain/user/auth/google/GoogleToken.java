package com.kusitms29.backendH.domain.user.auth.google;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GoogleToken {
    private static final String TOKEN_TYPE = "Bearer ";
    private String accessToken;

    public static GoogleToken createGoogleToken(String authToken) {
        return new GoogleToken(authToken);
    }

    public String getAccessTokenWithTokenType() {
        return TOKEN_TYPE + accessToken;
    }
}
