package com.kusitms29.backendH.api.sync.service;


import com.kusitms29.backendH.api.sync.service.dto.request.SyncBookmarkRequestDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncDetailResponseDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncGraphResponseDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncReviewResponseDto;
import com.kusitms29.backendH.domain.chat.service.RoomAppender;
import com.kusitms29.backendH.domain.sync.entity.*;
import com.kusitms29.backendH.domain.sync.service.*;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.global.error.ErrorCode;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import com.kusitms29.backendH.global.error.exception.ListException;
import com.kusitms29.backendH.infra.utils.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kusitms29.backendH.domain.chat.entity.Room.createRoom;
import static com.kusitms29.backendH.domain.sync.entity.FavoriteSync.createFavoriteSync;
import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_SYNC_TYPE;

@Service
@RequiredArgsConstructor
public class SyncDetailService {
    private final SyncReader syncReader;
    private final UserReader userReader;
    private final ParticipationManager participationManager;
    private final SyncManager syncManager;
    private final ParticipationReader participationReader;
    private final ListUtils listUtils;
    private final RoomAppender roomAppender;
    private final ParticipationAppender participationAppender;
    private final FavoriteSyncAppender favoriteSyncAppender;
    private final FavoriteSyncReader favoriteSyncReader;
    private final FavoriteSyncModifier favoriteSyncModifier;
    private final FavoriteSyncManager favoriteSyncManager;
    public SyncDetailResponseDto getSyncDetail(Long userId, Long syncId){
        Sync sync = syncReader.findById(syncId);
        User user = userReader.findByUserId(sync.getUser().getId());
        int count = participationManager.countParticipationBySyncId(syncId);
        Boolean isFull = syncManager.validateJoinRoom(sync,count);
        List<Sync> mySync = syncReader.findAllByUserId(userId);
        Boolean isOwner = syncManager.validateSync(mySync, sync);
        if (sync.getSyncType() == SyncType.ONETIME||sync.getSyncType() == SyncType.FROM_FRIEND) {
            return SyncDetailResponseDto.oneTimeOf(
                    sync.getSyncName(),
                    sync.getImage(),
                    sync.getSyncType(),
                    sync.getType(),
                    sync.getSyncIntro(),
                    sync.getDate(),
                    sync.getLocation(),
                    participationManager.countParticipationBySyncId(sync.getId()),
                    sync.getMember_max(),
                    user.getProfile(),
                    user.getUserName(),
                    user.getUniversity(),
                    sync.getUserIntro(),
                    isFull,
                    participationManager.existParticipation(userId, syncId),
                    favoriteSyncManager.existsByUserIdAndSyncId(userId,syncId),
                    isOwner
            );
        } else if (sync.getSyncType() == SyncType.LONGTIME) {
            return SyncDetailResponseDto.longTimeOf(
                    sync.getSyncName(),
                    sync.getImage(),
                    sync.getSyncType(),
                    sync.getType(),
                    sync.getSyncIntro(),
                    sync.getRegularDay(),
                    sync.getRegularTime(),
                    sync.getDate(),
                    sync.getLocation(),
                    participationManager.countParticipationBySyncId(sync.getId()),
                    sync.getMember_max(),
                    user.getProfile(),
                    user.getUserName(),
                    user.getUniversity(),
                    sync.getUserIntro(),
                    isFull,
                    participationManager.existParticipation(userId, syncId),
                    favoriteSyncManager.existsByUserIdAndSyncId(userId,syncId),
                    isOwner
            );
        } else {
            throw new InvalidValueException(INVALID_SYNC_TYPE);
        }
    }
    public SyncGraphResponseDto getSyncDetailGraph(Long syncId, String graph){
        List<Participation> participations = participationReader.findAllBySyncId(syncId);
        SyncGraphResponseDto graphElements = syncManager.createGraphElementList(participations, graph);
        return graphElements;
    }
    public List<SyncInfoResponseDto> getSyncListBySameDateAndSameLocation(Long userId, Long syncId, int take){
        Sync csync = syncReader.findById(syncId);
        List<Sync> syncList= syncReader.findAllByLocationAndDate(csync.getLocation(), csync.getDate());
        List<SyncInfoResponseDto> syncInfoResponseDtos = listUtils.getListByTake(syncList.stream()
                .filter(sync -> !sync.getId().equals(syncId))
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
                )).toList(), take);
        return ListException.throwIfEmpty(syncInfoResponseDtos, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    private final SyncReviewReader syncReviewReader;
    public List<SyncReviewResponseDto> getSyncReviewList(Long syncId, int take){
        List<SyncReview> syncReviews = syncReviewReader.findAllBySyncId(syncId);
        List<SyncReviewResponseDto> syncReviewResponseDtos = syncReviews.stream().
                map(syncReview -> SyncReviewResponseDto.of(
                        syncReview.getUser().getProfile(),
                        syncReview.getUser().getUserName(),
                        syncReview.getUser().getUniversity(),
                        syncReview.getContent(),
                        syncReview.getCreatedAt()
                )).toList();
        return listUtils.getListByTake(syncReviewResponseDtos, take);
    }
    public void joinSync(Long userId, Long syncId){
        Participation newParticipation =Participation.createParticipation(User.from(userId), Sync.from(syncId));
        participationAppender.saveParticipation(newParticipation);
        int count = participationManager.countParticipationBySyncId(syncId);
        Boolean isPossible = syncManager.validateCreateRoom(syncReader.findById(syncId),count);
        List<User> userList = participationReader.findAllBySyncId(syncId).stream().map(participation -> userReader.findByUserId(participation.getUser().getId())).toList();
        roomAppender.createRoom(userList,isPossible,syncId);
    }
    public Boolean bookmark(Long userId, SyncBookmarkRequestDto syncBookmarkRequestDto){
        if(syncBookmarkRequestDto.isMarked()){
            favoriteSyncModifier.deleteFavoriteSync(favoriteSyncReader.findByUserIdAndSyncId(userId,syncBookmarkRequestDto.syncId()));
            return false;
        }
        FavoriteSync favoriteSync = createFavoriteSync(User.from(userId),Sync.from(syncBookmarkRequestDto.syncId()));
        favoriteSyncAppender.saveFavoriteSync(favoriteSync);
        return true;
    }
}

