package com.kusitms29.backendH.domain.user.service;

import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.ErrorCode;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;

    public User findByUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public Optional<User> findByPlatformId(String platformId) {
        return userRepository.findByPlatformId(platformId);
    }
}
