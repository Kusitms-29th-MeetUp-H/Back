package com.kusitms29.backendH.domain.user.auth.kakao;

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

import static com.kusitms29.backendH.domain.user.auth.kakao.KakaoToken.createKakaoToken;
import static com.kusitms29.backendH.global.error.ErrorCode.JSON_PARSING_ERROR;


@RequiredArgsConstructor
@Component
public class KakaoAuthProvider {
    private final static String KAKAO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String HEADER_TYPE = "Authorization";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ResponseEntity<String> createGetRequest(String accessToken) {
        KakaoToken kakaoToken = createKakaoToken(accessToken);
        String kakaoAccessToken = kakaoToken.getAccessTokenWithTokenType();
        HttpEntity<String> request = createHttpEntityFromKakaoToken(kakaoAccessToken);
        return restTemplate.exchange(KAKAO_URL, HttpMethod.GET, request, String.class);
    }

    public KakaoUserInfo getKakaoUserInfoFromPlatformInfo(String platformInfo) {
        KakaoUserInfo kakaoUserInfo;
        try {
            kakaoUserInfo = objectMapper.readValue(platformInfo, KakaoUserInfo.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(JSON_PARSING_ERROR);
        }
        return kakaoUserInfo;
    }

    private HttpEntity<String> createHttpEntityFromKakaoToken(String kakaoAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_TYPE, kakaoAccessToken);
        return new HttpEntity<>(headers);
    }
}
