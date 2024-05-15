package com.kusitms29.backendH.domain.user.auth;

import com.kusitms29.backendH.domain.user.auth.google.GoogleAuthProvider;
import com.kusitms29.backendH.domain.user.auth.google.GoogleUserInfo;
import com.kusitms29.backendH.domain.user.auth.kakao.KakaoAuthProvider;
import com.kusitms29.backendH.domain.user.auth.kakao.KakaoUserInfo;
import com.kusitms29.backendH.domain.user.entity.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RestTemplateProvider {
    private final GoogleAuthProvider googleAuthProvider;
    private final KakaoAuthProvider kakaoAuthProvider;

    public PlatformUserInfo getUserInfoUsingRestTemplate(Platform platform, String accessToken) {
        ResponseEntity<String> platformResponse = getUserInfoFromPlatform(platform, accessToken);
        return getUserInfoFromPlatformInfo(platform, platformResponse.getBody());
    }

    private ResponseEntity<String> getUserInfoFromPlatform(Platform platform, String accessToken) {
        if (platform.equals(Platform.KAKAO))
            return kakaoAuthProvider.createGetRequest(accessToken);
        return googleAuthProvider.createGetRequest(accessToken);
    }

    private PlatformUserInfo getUserInfoFromPlatformInfo(Platform platform, String platformInfo) {
        if (platform.equals(Platform.KAKAO)) {
            KakaoUserInfo kakaoUserInfo = kakaoAuthProvider.getKakaoUserInfoFromPlatformInfo(platformInfo);
            return PlatformUserInfo.createPlatformUserInfo(
                    Long.toString(kakaoUserInfo.getId()),
                    kakaoUserInfo.getKakaoAccount().getEmail(),
                    getNickName(kakaoUserInfo),
                    getPicture(kakaoUserInfo));
        } else {
            GoogleUserInfo googleUserInfo = googleAuthProvider.getGoogleUserInfoFromPlatformInfo(platformInfo);
            return PlatformUserInfo.createPlatformUserInfo(
                    googleUserInfo.getId(),
                    googleUserInfo.getEmail(),
                    googleUserInfo.getName(),
                    googleUserInfo.getPicture());
        }
    }

    private String getNickName(KakaoUserInfo kakaoUserInfo) {
        if (kakaoUserInfo.getProperties() != null) {
            return kakaoUserInfo.getProperties().getNickname();
        } else {
            return "Unknown";
        }
    }

    private String getPicture(KakaoUserInfo kakaoUserInfo) {
        if (kakaoUserInfo.getProperties() != null) {
            return kakaoUserInfo.getProperties().getProfileImage();
        } else {
            return "Unknown";
        }
    }
}
