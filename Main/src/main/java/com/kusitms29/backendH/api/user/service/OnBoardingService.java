package com.kusitms29.backendH.api.user.service;

import com.kusitms29.backendH.api.user.service.dto.request.OnBoardingRequestDto;
import com.kusitms29.backendH.api.user.service.dto.response.OnBoardingResponseDto;
import com.kusitms29.backendH.domain.category.entity.Category;
import com.kusitms29.backendH.domain.category.service.CategoryReader;
import com.kusitms29.backendH.domain.category.service.UserCategoryModifier;
import com.kusitms29.backendH.domain.sync.entity.Gender;
import com.kusitms29.backendH.domain.sync.entity.Language;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.category.entity.UserCategory;
import com.kusitms29.backendH.domain.user.service.UserReader;
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

import static com.kusitms29.backendH.domain.category.entity.UserCategory.createUserCategory;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OnBoardingService {
    private final UniversityClient universityClient;
    private final AwsS3Service awsS3Service;

    private final UserReader userReader;
    private final CategoryReader categoryReader;
    private final UserCategoryModifier userCategoryModifier;

    @Transactional
    public void onBoardingUser(Long userId, MultipartFile profileImage, OnBoardingRequestDto requestDto) {
        User user = userReader.findByUserId(userId);

        Language lan = Language.getEnumLanguageFromStringLanguage(requestDto.getLanguage());
        String imageUrl = awsS3Service.uploadImage(profileImage);
        Gender gen = Gender.getEnumFROMStringGender(requestDto.getGender());
        SyncType syncType = SyncType.getEnumFROMStringSyncType(requestDto.getSyncType());
        universityClient.isValidUniversity(requestDto.getUniversity());

        user.updateOnBoardingWithoutCategory(lan.name(), imageUrl, requestDto.getUserName(),
                requestDto.getCountryName(), gen.name(), requestDto.getUniversity(), requestDto.getEmail(), syncType.name());

        List<Category> categories = requestDto.getDetailTypes().stream().map(
                        detailType -> categoryReader.findByName(detailType))
                .toList();
        userCategoryModifier.saveAll(categories.stream().map(category -> createUserCategory(user,category)).toList());
    }

//    private List<String> createUserCategory(User user, Map<String, Boolean> categoryMap) {
//        List<String> categoryNames = new ArrayList<>();
//        for (Map.Entry<String, Boolean> entry : categoryMap.entrySet()) {
//            if (entry.getValue()) {
//                Category category = categoryReader.findByName(entry.getKey());
//                UserCategory userCategory = UserCategory.builder()
//                        .user(user)
//                        .category(category)
//                        .build();
//                userCategoryModifier.save(userCategory);
//                categoryNames.add(category.getName());
//            }
//        }
//        return categoryNames;
//    }

}

