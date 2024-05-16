package com.kusitms29.backendH.api.user.service;

import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.api.user.service.dto.request.CreateReviewRequest;
import com.kusitms29.backendH.api.user.service.dto.response.CreateReviewResponse;
import com.kusitms29.backendH.api.user.service.dto.response.UserInfoResponseDto;
import com.kusitms29.backendH.domain.sync.entity.FavoriteSync;
import com.kusitms29.backendH.domain.sync.entity.Participation;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.entity.SyncReview;
import com.kusitms29.backendH.domain.sync.service.*;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.infra.utils.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                        sync.getDate()
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
                        sync.getDate()
                )).toList();
        return listUtils.getListByTake(syncInfoResponseDtos, take);
    }
    public UserInfoResponseDto getMyInfo(Long userId){
        return UserInfoResponseDto.of(userReader.findByUserId(userId));
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
                        sync.getDate()
                )).toList();
        return listUtils.getListByTake(syncInfoResponseDtos, take);
    }
}
