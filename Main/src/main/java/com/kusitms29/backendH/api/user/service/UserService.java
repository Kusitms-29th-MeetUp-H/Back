package com.kusitms29.backendH.api.user.service;

import com.kusitms29.backendH.api.community.service.dto.response.BannerImageResponseDto;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserReader userReader;

    public BannerImageResponseDto getLoginUserImage(Long userId) {
        User user = userReader.findByUserId(userId);
        String image = user.getProfile();
        return BannerImageResponseDto.of(image);
    }
}
