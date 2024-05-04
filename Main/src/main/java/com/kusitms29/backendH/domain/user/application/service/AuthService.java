package com.kusitms29.backendH.domain.user.application.service;

import com.kusitms29.backendH.domain.user.application.controller.dto.request.UserSignInRequestDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.response.UserAuthResponseDto;
import com.kusitms29.backendH.domain.user.auth.PlatformUserInfo;
import com.kusitms29.backendH.domain.user.auth.RestTemplateProvider;
import com.kusitms29.backendH.domain.user.domain.Platform;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.repository.RefreshTokenRepository;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.infra.config.auth.JwtProvider;
import com.kusitms29.backendH.infra.config.auth.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.kusitms29.backendH.domain.user.domain.Platform.getEnumPlatformFromStringPlatform;
import static com.kusitms29.backendH.domain.user.domain.RefreshToken.createRefreshToken;
import static com.kusitms29.backendH.global.error.ErrorCode.USER_NOT_FOUND;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplateProvider restTemplateProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserAuthResponseDto signIn(UserSignInRequestDto userSignInRequestDto, String authToken) {
        Platform platform = getEnumPlatformFromStringPlatform(userSignInRequestDto.getPlatform());
        PlatformUserInfo platformUser = getPlatformUserInfoFromRestTemplate(platform, authToken);
        User getUser = saveUser(platformUser, platform);
//        Boolean isFirstLogin = Objects.isNull(getUser.getUserType()) ? Boolean.TRUE : Boolean.FALSE;
        TokenInfo tokenInfo = issueAccessTokenAndRefreshToken(getUser);
        updateRefreshToken(tokenInfo.getRefreshToken(), getUser);
        return UserAuthResponseDto.of(getUser, tokenInfo/*, isFirstLogin*/);
    }

    public void signOut(Long userId) {
        User findUser = getUserFromUserId(userId);
        deleteRefreshToken(findUser);
    }

    private void deleteRefreshToken(User user) {
        user.updateRefreshToken(null);
        refreshTokenRepository.deleteById(user.getId());
    }

    private User saveUser(PlatformUserInfo platformUserInfo, Platform platform) {
        User createdUser = getUserByPlatformUserInfo(platformUserInfo, platform);
        return userRepository.save(createdUser);
    }

    private void updateRefreshToken(String refreshToken, User user) {
        user.updateRefreshToken(refreshToken);
        refreshTokenRepository.save(createRefreshToken(user.getId(), refreshToken));
    }

    private TokenInfo issueAccessTokenAndRefreshToken(User user) {
        return jwtProvider.issueToken(user.getId());
    }

    private User getUserFromUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private User getUserByPlatformUserInfo(PlatformUserInfo platformUserInfo, Platform platform) {
        return userRepository.findByPlatformId(platformUserInfo.getId())
                .orElse(User.createUser(platformUserInfo, platform, generateRandomUuid(platformUserInfo)));
    }

    private PlatformUserInfo getPlatformUserInfoFromRestTemplate(Platform platform, String authToken) {
        return restTemplateProvider.getUserInfoUsingRestTemplate(platform, authToken);
    }

    private String generateRandomUuid(PlatformUserInfo platformUserInfo) {
        UUID randomUuid = UUID.randomUUID();
        String uuidAsString = randomUuid.toString().replace("-", "");
        return platformUserInfo.getId() + "_" + uuidAsString.substring(0, 6);
    }

}

