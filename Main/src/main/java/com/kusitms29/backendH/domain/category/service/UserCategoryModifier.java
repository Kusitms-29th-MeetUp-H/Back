package com.kusitms29.backendH.domain.category.service;

import com.kusitms29.backendH.domain.category.entity.UserCategory;
import com.kusitms29.backendH.domain.category.repository.CategoryRepository;
import com.kusitms29.backendH.domain.category.repository.UserCategoryRepository;
import com.kusitms29.backendH.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserCategoryModifier {
    private final UserCategoryRepository userCategoryRepository;

    public void save(UserCategory userCategory) {
        userCategoryRepository.save(userCategory);
    }
    public void deleteAllByUserId(Long userId){ userCategoryRepository.deleteAllByUserId(userId);}
    public void saveAll(List<UserCategory> userCategories){ userCategoryRepository.saveAll(userCategories);}
}
