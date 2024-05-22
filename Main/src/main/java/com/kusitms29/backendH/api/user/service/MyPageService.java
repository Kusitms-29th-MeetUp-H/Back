package com.kusitms29.backendH.api.user.service;

import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.api.user.service.dto.request.CreateReviewRequest;
import com.kusitms29.backendH.api.user.service.dto.request.EditProfileReq;
import com.kusitms29.backendH.api.user.service.dto.request.EditProfileRequest;
import com.kusitms29.backendH.api.user.service.dto.response.CreateReviewResponse;
import com.kusitms29.backendH.api.user.service.dto.response.UserInfoResponseDto;
import com.kusitms29.backendH.domain.category.entity.Category;
import com.kusitms29.backendH.domain.category.entity.UserCategory;
import com.kusitms29.backendH.domain.category.service.CategoryReader;
import com.kusitms29.backendH.domain.category.service.UserCategoryModifier;
import com.kusitms29.backendH.domain.category.service.UserCategoryReader;
import com.kusitms29.backendH.domain.sync.entity.*;
import com.kusitms29.backendH.domain.sync.service.*;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserModifier;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.infra.config.AwsS3Service;
import com.kusitms29.backendH.infra.utils.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MyPageService {
    private final ParticipationManager participationManager;
    private final ListUtils listUtils;
    private final SyncReader syncReader;
    private final ParticipationReader participationReader;
    private final UserReader userReader;
    private final SyncReviewAppender syncReviewAppender;
    private final FavoriteSyncReader favoriteSyncReader;
    private final UserCategoryModifier userCategoryModifier;
    private final AwsS3Service awsS3Service;
    private final CategoryReader categoryReader;
    private final UserCategoryReader userCategoryReader;
    private final FavoriteSyncManager favoriteSyncManager;
    public List<SyncInfoResponseDto> getMySyncList(Long userId, int take){
        List<Sync> syncList = syncReader.findAllByUserId(userId);
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncList.stream()
                .map( sync -> SyncInfoResponseDto.of(
                        sync.getId(),
                        sync.getSyncType(),
                        sync.getType(),
                        sync.getImage(),
                        participationManager.countParticipationBySyncId(sync.getId()),
                        sync.getMember_max(),
                        sync.getSyncName(),
                        sync.getLocation(),
                        sync.getDate(),
                        favoriteSyncManager.existsByUserIdAndSyncId(userId, sync.getId())
                )).toList();
        return listUtils.getListByTake(syncInfoResponseDtos, take);
    }
    public List<SyncInfoResponseDto> getJoinSyncList(Long userId, int take){
        List<Participation> participations = participationReader.findAllByUserId(userId);
        List<Sync> syncList = participations.stream().map(participation -> syncReader.findById(participation.getSync().getId())).toList();
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncList.stream()
                .map( sync -> SyncInfoResponseDto.of(
                        sync.getId(),
                        sync.getSyncType(),
                        sync.getType(),
                        sync.getImage(),
                        participationManager.countParticipationBySyncId(sync.getId()),
                        sync.getMember_max(),
                        sync.getSyncName(),
                        sync.getLocation(),
                        sync.getDate(),
                        favoriteSyncManager.existsByUserIdAndSyncId(userId, sync.getId())
                )).toList();
        return listUtils.getListByTake(syncInfoResponseDtos, take);
    }
    public UserInfoResponseDto getMyInfo(Long userId){
        List<String> detailTypes = userCategoryReader.findAllByUserId(userId).stream().map(userCategory -> userCategory.getCategory().getName()).toList();
        return UserInfoResponseDto.of(userReader.findByUserId(userId),detailTypes);
    }
    public CreateReviewResponse createReview(Long userId, CreateReviewRequest createReviewRequest){
        SyncReview syncReview = SyncReview.createReview(User.from(userId),Sync.from(createReviewRequest.syncId()), createReviewRequest.content() );
        syncReview = syncReviewAppender.createReview(syncReview);
        return CreateReviewResponse.of(syncReview);
    }
    public List<SyncInfoResponseDto> getBookMarkSyncList(Long userId, int take){
        List<FavoriteSync> favoriteSyncs = favoriteSyncReader.findAllByUserId(userId);
        List<Sync> syncList = favoriteSyncs.stream().map(favoriteSync -> syncReader.findById(favoriteSync.getSync().getId())).toList();
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncList.stream()
                .map( sync -> SyncInfoResponseDto.of(
                        sync.getId(),
                        sync.getSyncType(),
                        sync.getType(),
                        sync.getImage(),
                        participationManager.countParticipationBySyncId(sync.getId()),
                        sync.getMember_max(),
                        sync.getSyncName(),
                        sync.getLocation(),
                        sync.getDate(),
                        favoriteSyncManager.existsByUserIdAndSyncId(userId, sync.getId())
                )).toList();
        return listUtils.getListByTake(syncInfoResponseDtos, take);
    }
    @Transactional
    public void editProfile(Long userId, MultipartFile profileImage, EditProfileRequest editProfileRequest){
        System.out.println("userId: " + userId);
//        System.out.println("editProfileRequest.image(): " + editProfileRequest.image());
        System.out.println("editProfileRequest.name(): " + editProfileRequest.name());
        System.out.println("editProfileRequest.gender(): " + editProfileRequest.gender());
        System.out.println("editProfileRequest.syncType(): " + editProfileRequest.syncType());
        System.out.println("editProfileRequest.detailTypes(): " + editProfileRequest.detailTypes());

        User user = userReader.findByUserId(userId);
        String image = awsS3Service.uploadImage(profileImage);
        user.updateProfile(image,editProfileRequest.name(), Gender.getEnumFROMStringGender(editProfileRequest.gender()), SyncType.getEnumFROMStringSyncType(editProfileRequest.syncType()));
        userCategoryModifier.deleteAllByUserId(user.getId());
        List<Category> categories = editProfileRequest.detailTypes().stream().map(
                detailType -> categoryReader.findByName(detailType))
                .toList();
        userCategoryModifier.saveAll(categories.stream().map(category -> UserCategory.createUserCategory(user,category)).toList());
    }
    @Transactional
    public void editProfiles(Long userId, EditProfileReq editProfileReq){

        User user = userReader.findByUserId(userId);
        user.updateProfile(editProfileReq.image(),editProfileReq.name(), Gender.getEnumFROMStringGender(editProfileReq.gender()), SyncType.getEnumFROMStringSyncType(editProfileReq.syncType()));
        userCategoryModifier.deleteAllByUserId(user.getId());
        List<Category> categories = editProfileReq.detailTypes().stream().map(
                        detailType -> categoryReader.findByName(detailType))
                .toList();
        userCategoryModifier.saveAll(categories.stream().map(category -> UserCategory.createUserCategory(user,category)).toList());
    }
    public String imageUpload(MultipartFile file){
        return awsS3Service.uploadImage(file);
    }
}
