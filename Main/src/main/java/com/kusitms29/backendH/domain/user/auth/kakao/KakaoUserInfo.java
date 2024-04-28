package com.kusitms29.backendH.domain.user.auth.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoUserInfo {
    private Long id;
    private KakaoAccount kakaoAccount;
    private Properties properties;

    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class KakaoAccount {
        private boolean hasEmail;
        private String email;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Properties {
        private String nickname;
        private String profileImage;
    }
}
