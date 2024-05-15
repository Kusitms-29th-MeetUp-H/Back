package com.kusitms29.backendH.domain.category.service;

import com.kusitms29.backendH.domain.category.entity.UserCategory;
import com.kusitms29.backendH.domain.category.repository.UserCategoryRepository;
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
