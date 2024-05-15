package com.kusitms29.backendH.domain.user.service;

import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserModifier {
    private final UserRepository userRepository;

    public User save(User user) {
       return userRepository.save(user);
    }
}
