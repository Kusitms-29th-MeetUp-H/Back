package com.kusitms29.backendH.api.user.service.dto.response;

import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.infra.config.auth.TokenInfo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAuthResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String picture;
    private String accessToken;
    private String refreshToken;
    private Boolean isFirst;
    private String sessionId;

    public static UserAuthResponseDto of(User user, TokenInfo token, Boolean isFirst) {
        return UserAuthResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getUserName())
                .picture(user.getProfile())
                .isFirst(isFirst)
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .sessionId(user.getSessionId())
                .build();
    }
}
