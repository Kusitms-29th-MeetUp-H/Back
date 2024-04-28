package com.kusitms29.backendH.domain.user.auth.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms29.backendH.global.error.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.kusitms29.backendH.domain.user.auth.google.GoogleToken.createGoogleToken;
import static com.kusitms29.backendH.global.error.ErrorCode.JSON_PARSING_ERROR;


@RequiredArgsConstructor
@Component
public class GoogleAuthProvider {
    private static final String GOOGLE_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final String HEADER_TYPE = "Authorization";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ResponseEntity<String> createGetRequest(String accessToken) {
        GoogleToken googleToken = createGoogleToken(accessToken);
        String googleAccessToken = googleToken.getAccessTokenWithTokenType();
        HttpEntity<String> request = createHttpEntityFromGoogleToken(googleAccessToken);
        return restTemplate.exchange(GOOGLE_URL, HttpMethod.GET, request, String.class);
    }

    public GoogleUserInfo getGoogleUserInfoFromPlatformInfo(String platformInfo) {
        GoogleUserInfo googleUserInfo;
        try {
            googleUserInfo = objectMapper.readValue(platformInfo, GoogleUserInfo.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(JSON_PARSING_ERROR);
        }
        return googleUserInfo;
    }

    private HttpEntity<String> createHttpEntityFromGoogleToken(String googleAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_TYPE, googleAccessToken);
        return new HttpEntity<>(headers);
    }
}
