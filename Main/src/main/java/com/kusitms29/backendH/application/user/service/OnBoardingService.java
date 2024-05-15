package com.kusitms29.backendH.application.user.service;

import com.kusitms29.backendH.application.user.service.dto.request.OnBoardingRequestDto;
import com.kusitms29.backendH.application.user.service.dto.response.OnBoardingResponseDto;
import com.kusitms29.backendH.domain.category.domain.Category;
import com.kusitms29.backendH.domain.category.repository.CategoryRepository;
import com.kusitms29.backendH.domain.sync.domain.Gender;
import com.kusitms29.backendH.domain.sync.domain.Language;
import com.kusitms29.backendH.domain.sync.domain.SyncType;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.UserCategory;
import com.kusitms29.backendH.domain.user.repository.UserCategoryRepository;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.infra.config.AwsS3Service;
import com.kusitms29.backendH.infra.external.UniversityClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.kusitms29.backendH.global.error.ErrorCode.CATEGORY_NOT_FOUND;
import static com.kusitms29.backendH.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OnBoardingService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final UniversityClient universityClient;
    private final AwsS3Service awsS3Service;

    @Transactional
    public OnBoardingResponseDto onBoardingUser(Long userId, MultipartFile profileImage, OnBoardingRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        Language lan = Language.getEnumLanguageFromStringLanguage(requestDto.getLanguage());
        String imageUrl = awsS3Service.uploadImage(profileImage);
        Gender gen = Gender.getEnumFROMStringGender(requestDto.getGender());
        SyncType syncType = SyncType.getEnumFROMStringSyncType(requestDto.getSyncType());
        universityClient.isValidUniversity(requestDto.getUniversity());

        user.updateOnBoardingWithoutCategory(lan.name(), imageUrl, requestDto.getUserName(),
                requestDto.getCountryName(), gen.name(), requestDto.getUniversity(), requestDto.getEmail(), syncType.name());

        List<String> categoryNames = new ArrayList<>();

        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getForeignLanguage()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getCultureArt()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getTravelCompanion()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getActivity()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getFoodAndDrink()));
        categoryNames.addAll(createUserCategory(user, requestDto.getCategoryTypes().getEtc()));

        return OnBoardingResponseDto.of(user.getLanguage().getStringLanguage(), imageUrl, user.getUserName(), user.getNationality(),
                user.getGender().getStringGender(), user.getUniversity(), user.getEmail(), user.getSyncType().getStringSyncType(), categoryNames);
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

