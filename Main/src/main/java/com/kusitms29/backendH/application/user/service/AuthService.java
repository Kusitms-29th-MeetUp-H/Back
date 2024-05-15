package com.kusitms29.backendH.application.user.service;


import com.kusitms29.backendH.application.user.service.dto.request.UserSignInRequestDto;
import com.kusitms29.backendH.application.user.service.dto.response.UserAuthResponseDto;
import com.kusitms29.backendH.domain.fcm.application.service.SyncReminderService;
import com.kusitms29.backendH.domain.user.auth.PlatformUserInfo;
import com.kusitms29.backendH.domain.user.auth.RestTemplateProvider;
import com.kusitms29.backendH.domain.user.entity.Platform;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.repository.RefreshTokenRepository;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.infra.config.auth.JwtProvider;
import com.kusitms29.backendH.infra.config.auth.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.kusitms29.backendH.domain.user.entity.Platform.getEnumPlatformFromStringPlatform;
import static com.kusitms29.backendH.domain.user.entity.RefreshToken.createRefreshToken;
import static com.kusitms29.backendH.global.error.ErrorCode.FCMTOKEN_NOT_FOUND;
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
    private final SyncReminderService syncReminderService;

    public UserAuthResponseDto signIn(UserSignInRequestDto userSignInRequestDto, String authToken, String fcmToken) {
        if (fcmToken == null || fcmToken.isEmpty()) {
            throw new EntityNotFoundException(FCMTOKEN_NOT_FOUND);
        }
        Platform platform = getEnumPlatformFromStringPlatform(userSignInRequestDto.getPlatform());
        PlatformUserInfo platformUser = getPlatformUserInfoFromRestTemplate(platform, authToken);
        User getUser = saveUser(platformUser, platform, fcmToken);
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

    private User saveUser(PlatformUserInfo platformUserInfo, Platform platform, String fcmToken) {
        User createdUser = getUserByPlatformUserInfo(platformUserInfo, platform, fcmToken);
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

    private User getUserByPlatformUserInfo(PlatformUserInfo platformUserInfo, Platform platform, String fcmToken) {
        Optional<User> optionalUser = userRepository.findByPlatformId(platformUserInfo.getId());
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            User getUser = User.createUser(platformUserInfo, platform, generateRandomUuid(platformUserInfo));
            syncReminderService.saveToken(String.valueOf(getUser.getId()), fcmToken);
            return getUser;
        }
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


