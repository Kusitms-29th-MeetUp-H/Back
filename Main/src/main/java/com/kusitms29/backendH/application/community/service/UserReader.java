package com.kusitms29.backendH.application.community.service;

import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserReader {
    public static UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
    }

}
