package com.kusitms29.backendH.domain.user.domain.service;

import com.kusitms29.backendH.domain.user.domain.UserCategory;
import com.kusitms29.backendH.domain.user.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryReader {
    private final UserCategoryRepository userCategoryRepository;
    public List<UserCategory> findAllByUserId(Long userId){
        return userCategoryRepository.findAllByUserId(userId);
    }
}
