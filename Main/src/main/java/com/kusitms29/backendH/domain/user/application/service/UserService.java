package com.kusitms29.backendH.domain.user.application.service;

import com.kusitms29.backendH.domain.category.domain.Category;
import com.kusitms29.backendH.domain.category.repository.CategoryRepository;
import com.kusitms29.backendH.domain.sync.domain.Gender;
import com.kusitms29.backendH.domain.sync.domain.Language;
import com.kusitms29.backendH.domain.sync.domain.SyncType;
import com.kusitms29.backendH.domain.user.application.controller.dto.request.OnBoardingRequestDto;
import com.kusitms29.backendH.domain.user.application.controller.dto.response.OnBoardingResponseDto;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.UserCategory;
import com.kusitms29.backendH.domain.user.repository.UserCategoryRepository;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.kusitms29.backendH.global.error.ErrorCode.CATEGORY_NOT_FOUND;
import static com.kusitms29.backendH.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;

    public OnBoardingResponseDto onBoardingUser(Long userId, OnBoardingRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        Language lan = Language.getEnumLanguageFromStringLanguage(requestDto.getLanguage());
        Gender gen = Gender.getEnumFROMStringGender(requestDto.getGender());
        SyncType syncType = SyncType.getEnumFROMStringSyncType(requestDto.getSyncType());

        user.updateOnBoardingWithoutCategory(lan.name(), requestDto.getUserName(),
                requestDto.getCountryName(), gen.name(), requestDto.getEmail(), syncType.name());

        List<String> categoryNames = new ArrayList<>();

        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getForeignLanguage()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getCultureArt()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getTravelCompanion()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getActivity()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getFoodAndDrink()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getEtc()));

        return OnBoardingResponseDto.of(user.getLanguage().getStringLanguage(), user.getUserName(), user.getNationality(),
                user.getGender().getStringGender(), user.getEmail(), user.getSyncType().getStringSyncType(), categoryNames);
    }

    private List<String> createUserCategory(User user, Map<String, Boolean> categoryMap) {
        List<String> categoryNames = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : categoryMap.entrySet()) {
            if (entry.getValue()) {
                Category category = categoryRepository.findByName(entry.getKey())
                        .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
                UserCategory userCategory = UserCategory.builder()
                        .user(user)
                        .category(category)
                        .build();
                userCategoryRepository.save(userCategory);
                categoryNames.add(category.getName());
            }
        }
        return categoryNames;
    }

}
