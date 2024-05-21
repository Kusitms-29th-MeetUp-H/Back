package com.kusitms29.backendH.global.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms29.backendH.infra.config.auth.UserId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HealthCheckApiController {
    private static final String GOOGLE_AUTH_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String GOOGLE_TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";
    private static final String KAKAO_AUTH_ENDPOINT = "https://kauth.kakao.com/oauth/authorize";
    private static final String KAKAO_TOKEN_ENDPOINT = "https://kauth.kakao.com/oauth/token";

    @Value("${app.google.client.id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${app.google.client.secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${app.google.callback.url}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${app.kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${app.kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${app.kakao.callback.url}")
    private String KAKAO_REDIRECT_URI;
    @GetMapping("google")
    public ResponseEntity<String> googleOauth(HttpServletRequest request) throws IOException {
        String code = extractCode(request);

        if (code == null) {
            String authUrl = GOOGLE_AUTH_ENDPOINT +
                    "?client_id=" + GOOGLE_CLIENT_ID +
                    "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                    "&response_type=code" +
                    "&scope=email%20profile";

            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, authUrl).build();
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String accessToken = restTemplate.postForObject(GOOGLE_TOKEN_ENDPOINT +
                    "?client_id=" + GOOGLE_CLIENT_ID +
                    "&client_secret=" + GOOGLE_CLIENT_SECRET +
                    "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                    "&code=" + code +
                    "&grant_type=authorization_code", null, String.class);

            // Access Token을 이용한 추가 처리 로직 작성
            // ...

            return new ResponseEntity<>(accessToken, HttpStatus.OK);
        }
    }

    private String extractCode(HttpServletRequest request) {
        String fullUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();

        if (queryString != null && queryString.contains("code=")) {
            return queryString.split("code=")[1].split("&")[0];
        }

        return null;
    }
    @GetMapping("/oauth/google")
    public void googleOauth(HttpServletResponse response) throws IOException {
        String authUrl = GOOGLE_AUTH_ENDPOINT +
                "?client_id=" + GOOGLE_CLIENT_ID +
                "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                "&response_type=code" +
                "&scope=email%20profile";

        response.sendRedirect(authUrl);
    }

    @GetMapping("/oauth/google/callback")
    public ResponseEntity<String> googleOauthCallback(@RequestParam(name = "code") String code) {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = restTemplate.postForObject(GOOGLE_TOKEN_ENDPOINT +
                "?client_id=" + GOOGLE_CLIENT_ID +
                "&client_secret=" + GOOGLE_CLIENT_SECRET +
                "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                "&code=" + code +
                "&grant_type=authorization_code", null, String.class);

        // Access Token을 이용한 추가 처리 로직 작성
        // ...

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @GetMapping("/oauth/kakao")
    public void kakaoOauth(HttpServletResponse response) throws IOException {
        String authUrl = KAKAO_AUTH_ENDPOINT +
                "?client_id=" + KAKAO_CLIENT_ID +
                "&redirect_uri=" + KAKAO_REDIRECT_URI +
                "&response_type=code";

        response.sendRedirect(authUrl);
    }

    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<String> kakaoOauthCallback(@RequestParam(name = "code") String code) {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = restTemplate.postForObject(KAKAO_TOKEN_ENDPOINT +
                "?grant_type=authorization_code" +
                "&client_id=" + KAKAO_CLIENT_ID +
                "&client_secret=" + KAKAO_CLIENT_SECRET +
                "&redirect_uri=" + KAKAO_REDIRECT_URI +
                "&code=" + code, null, String.class);

        // Access Token을 이용한 추가 처리 로직 작성
        // ...

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
    @RequestMapping("/")
    public Long MeetUpServer(@UserId Long userId) {
        return userId;
    }

}