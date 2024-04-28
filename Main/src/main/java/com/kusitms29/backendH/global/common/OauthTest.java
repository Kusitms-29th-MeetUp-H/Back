package com.kusitms29.backendH.global.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OauthTest {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${app.google.client.id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${app.google.client.secret}")
    private String GOOGLE_CLIENT_SECRET ;
    @Value("${app.google.callback.url}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${app.kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${app.kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET ;
    @Value("${app.kakao.callback.url}")
    private String KAKAO_REDIRECT_URI;


    private static final String GRANT_TYPE = "authorization_code";

    public OauthTest(RestTemplate restTemplate) {
        this.objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 이 부분을 추가
        this.restTemplate = restTemplate;
    }
    public ResponseEntity<String> createPostKakaoRequest(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("client_secret", KAKAO_CLIENT_SECRET);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("grant_type", GRANT_TYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

    public ResponseEntity<String> createPostGoogleRequest(String code) {
        String url = "https://oauth2.googleapis.com/token";

        //MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_REDIRECT_URI);
        params.put("grant_type", GRANT_TYPE);

        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-Type", "application/x-www-form-urlencoded");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, params, String.class);
        // HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

//        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return responseEntity;
    }

//    public OAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
////        OAuthToken oAuthToken = null;
////        try {
////            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////        }
////        return oAuthToken;
//        return objectMapper.readValue(response.getBody(), OAuthToken.class);
//    }

    public ResponseEntity<String> createGetRequest(String socialLoginType, OAuthToken oAuthToken) {
        String url;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

        if ("kakao".equals(socialLoginType)) {
            // 카카오 URL을 선택
            url = "https://kapi.kakao.com/v2/user/me";
        } else if ("google".equals(socialLoginType)) {
            // Google URL을 선택
            url = "https://www.googleapis.com/oauth2/v1/userinfo";
        } else {
            // socialLoginType이 지원되지 않는 경우 예외처리 또는 기본 URL 설정
            throw new IllegalArgumentException("Unsupported social login type: " + socialLoginType);
        }

        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    }


    public ResponseEntity<String> createGetRequest(OAuthToken oAuthToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response;
    }
//    public GoogleUser getGoogleUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException{
//
//        return objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
//    }
//    public KakaoUser getKakaoUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException{
//
//        return objectMapper.readValue(userInfoResponse.getBody(), KakaoUser.class);
//    }
}
